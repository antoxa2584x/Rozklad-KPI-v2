package com.goldenpiedevs.schedule.app.ui.choose.group

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.api.group.GroupManager
import com.goldenpiedevs.schedule.app.core.api.lessons.LessonsManager
import com.goldenpiedevs.schedule.app.core.api.teachers.TeachersManager
import com.goldenpiedevs.schedule.app.core.dao.group.DaoGroupModel
import com.goldenpiedevs.schedule.app.ui.base.BasePresenterImpl
import com.goldenpiedevs.schedule.app.ui.main.MainActivity
import com.jakewharton.rxbinding2.widget.RxAutoCompleteTextView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.blurry.Blurry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
    @Inject
    lateinit var teachersManager: TeachersManager

    private lateinit var autoCompleteTextView: AutoCompleteTextView

    override fun requestPermissions() {
        with(view.getContext() as Activity) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED)
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        }
    }

    override fun setAutocompleteTextView(autoCompleteTextView: AutoCompleteTextView) {
        this.autoCompleteTextView =
                autoCompleteTextView.also {
                    addOnAutoCompleteTextViewItemClickedSubscriber(it)
                    addOnAutoCompleteTextViewTextChangedObserver(it)
                    addOnSendClickHandle(it)
                }
    }

    private fun addOnSendClickHandle(autoCompleteTextView: AutoCompleteTextView) {
        autoCompleteTextView.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                awaitNextScreen(autoCompleteTextView.text.toString().replace("И", "i"))
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun showMainScreen() {
        with(view as AppCompatActivity) {
            when (callingActivity == null) {
                true -> {
                    startActivity(Intent(this, MainActivity::class.java))
                }
                false -> {
                    setResult(Activity.RESULT_OK)
                }
            }
            finish()
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
                    it.text().toString().replace("И", "i")
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

                            val list = it.body()!!.data!!

                            val itemsAdapter = ArrayAdapter<DaoGroupModel>(view.getContext(),
                                    R.layout.centered_material_list_item, list)

                            autoCompleteTextView.setAdapter<ArrayAdapter<DaoGroupModel>>(itemsAdapter)

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
                    val item = autoCompleteTextView.adapter.getItem(it.position()) as DaoGroupModel
                    item.groupId
                }
                .observeOn(Schedulers.io())
                .switchMap { groupManager.groupDetails(it) }
                .observeOn(AndroidSchedulers.mainThread())

        compositeDisposable.add(
                adapterViewItemClickEventObservable.subscribe(
                        { awaitNextScreen(it.body()?.data?.groupId.toString()) },
                        { view.onError() }))
    }

    private fun awaitNextScreen(groupId: String) {
        view.showProgressDialog()

        GlobalScope.launch {
            val successfulList = mutableListOf(false, false)
            successfulList[0] = lessonsManager.loadTimeTableAsync(groupId).await()
            successfulList[1] = teachersManager.loadTeachersAsync(groupId).await()

            launch(Dispatchers.Main) {
                view.dismissProgressDialog()

                if (successfulList.all { it }) {
                    showMainScreen()
                } else {
                    view.onError()
                }
            }

        }
    }

}