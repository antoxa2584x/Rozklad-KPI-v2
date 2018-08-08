package com.goldenpiedevs.schedule.app.ui.choose.group

import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.api.group.GroupManager
import com.goldenpiedevs.schedule.app.core.api.lessons.LessonsManager
import com.goldenpiedevs.schedule.app.core.dao.group.GroupModel
import com.goldenpiedevs.schedule.app.ui.base.BasePresenterImpl
import com.goldenpiedevs.schedule.app.ui.main.MainActivity
import com.jakewharton.rxbinding2.widget.RxAutoCompleteTextView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.blurry.Blurry
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class ChooseGroupImplementation : BasePresenterImpl<ChooseGroupView>(), ChooseGroupPresenter {

    companion object {
        private const val MIN_LENGTH_TO_START = 2
    }

    @Inject
    lateinit var groupManager: GroupManager
    @Inject
    lateinit var lessonsManager: LessonsManager

    private lateinit var autoCompleteTextView: AutoCompleteTextView

    override fun setAutocompleteTextView(autoCompleteTextView: AutoCompleteTextView) {
        this.autoCompleteTextView =
                autoCompleteTextView.also {
                    addOnAutoCompleteTextViewItemClickedSubscriber(it)
                    addOnAutoCompleteTextViewTextChangedObserver(it)
                }
    }

    private fun showMainScreen() {
        with(view as AppCompatActivity) {
            finish()
            startActivity(Intent(view.getContext(), MainActivity::class.java))
        }
    }

    override fun blurView(view: View) {
        Blurry.with(view.context)
                .radius(10)
                .sampling(8)
                .color(ContextCompat.getColor(view.context, R.color.blur))
                .async()
                .from(BitmapFactory.decodeResource(view.resources, R.drawable.init_screen_back))
                .into(view as ImageView?)
    }

    private fun addOnAutoCompleteTextViewTextChangedObserver(autoCompleteTextView: AutoCompleteTextView) {
        val subscription = RxTextView.textChangeEvents(autoCompleteTextView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .filter { it.text().length >= MIN_LENGTH_TO_START }
                .map {
                    val str = it.text().toString().toUpperCase()
                    if (str.contains("И")) str.replace("И", "i")
                    str
                }
                .switchMap {
                    groupManager.autocomplete(it)
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

                            val list = it.body()!!.data
                            val itemsAdapter = ArrayAdapter<GroupModel>(view.getContext(),
                                    R.layout.centered_material_list_item, list!!)

                            autoCompleteTextView.setAdapter<ArrayAdapter<GroupModel>>(itemsAdapter)

                            if (!list.isNotEmpty()) {
                                autoCompleteTextView.dismissDropDown()
                            } else {
                                autoCompleteTextView.showDropDown()
                            }
                        },
                        { Log.e(TAG, "onError", it) }))
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

        compositeDisposable.add(
                adapterViewItemClickEventObservable.subscribe(
                        { awaitNextScreen(it.body()!!.data) },
                        { view.onError() }))
    }

    private fun awaitNextScreen(body: GroupModel?) {
        view.showProgreeDialog()

        launch {
            val isSuccessful = lessonsManager.loadTimeTable(body!!.groupId).await()

            launch(UI) {
                view.dismissProgreeDialog()

                if (isSuccessful) {
                    showMainScreen()
                } else {
                    view.onError()
                }
            }

        }
    }

}