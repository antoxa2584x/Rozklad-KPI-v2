package com.goldenpiedevs.schedule.app.ui.launcher

import android.content.Intent
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.api.group.GroupManager
import com.goldenpiedevs.schedule.app.core.dao.GroupModel
import com.goldenpiedevs.schedule.app.core.utils.AppPreference
import com.goldenpiedevs.schedule.app.ui.base.BasePresenterImpl
import com.jakewharton.rxbinding2.widget.RxAutoCompleteTextView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class LauncherImplementation : BasePresenterImpl<LauncherView>(), LauncherPresenter {
    private val MIN_LENGTH_TO_START = 2

    @Inject
    lateinit var groupManager: GroupManager

    private lateinit var autoCompleteTextView: AutoCompleteTextView

    override fun setAutocompleteTextView(autoCompleteTextView: AutoCompleteTextView) {
        this.autoCompleteTextView = autoCompleteTextView

        addOnAutoCompleteTextViewItemClickedSubscriber(this.autoCompleteTextView)
        addOnAutoCompleteTextViewTextChangedObserver(this.autoCompleteTextView)
    }

    override fun showNextScreen() {
        if (AppPreference.isFirstLauch) {
            showInitView()
        } else {
            showMainScreen()
        }
    }

    private fun showMainScreen() {
        view.getContext().startActivity(Intent(view.getContext(), Object::class.java)) //FIXME: Change to Main Activity
    }

    private fun showInitView() {
        view.showGroupChooserView()
    }

    override fun onGroupNameInputUpdated(input: String) {

    }

    private fun addOnAutoCompleteTextViewTextChangedObserver(autoCompleteTextView: AutoCompleteTextView) {
        val subscription = RxTextView.textChangeEvents(autoCompleteTextView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .filter { it.text().length >= MIN_LENGTH_TO_START }
                .switchMap {
                    groupManager.autocomplete(it.text().toString().toUpperCase())
                            .onErrorResumeNext(Observable.empty())
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        compositeDisposable.add(
                subscription.subscribe(
                        {
                            if (!it.isSuccessful) {
                                autoCompleteTextView.dismissDropDown()
                                return@subscribe
                            }

                            val list = it.body()
                            val itemsAdapter = ArrayAdapter<GroupModel>(view.getContext(),
                                    R.layout.centered_material_list_item, list!!.data)

                            autoCompleteTextView.setAdapter<ArrayAdapter<GroupModel>>(itemsAdapter)

                            if (!list.data!!.isNotEmpty()) {
                                autoCompleteTextView.dismissDropDown()
                            } else {
                                autoCompleteTextView.showDropDown()
                            }
                        },
                        { Log.e(TAG, "onError", it) },
                        { Log.i(TAG, "onCompleted") }))
    }

    private fun addOnAutoCompleteTextViewItemClickedSubscriber(autoCompleteTextView: AutoCompleteTextView) {
        val adapterViewItemClickEventObservable = RxAutoCompleteTextView.itemClickEvents(autoCompleteTextView)
                .map {
                    val item = autoCompleteTextView.adapter.getItem(it.position()) as GroupModel
                    item.groupId
                }
                .observeOn(Schedulers.io())
                .switchMap { groupManager.groupDetails(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .retry()

        compositeDisposable.add(
                adapterViewItemClickEventObservable.subscribe(
                        { awaitNextScreen(it.body()!!) },
                        { throwable -> Log.e(TAG, "onError", throwable) },
                        { Log.i(TAG, "onCompleted") }))
    }

    private fun awaitNextScreen(body: GroupModel) {

    }

}