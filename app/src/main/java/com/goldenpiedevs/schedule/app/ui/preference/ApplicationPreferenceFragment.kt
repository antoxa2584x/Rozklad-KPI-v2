package com.goldenpiedevs.schedule.app.ui.preference

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.ScheduleApplication
import com.goldenpiedevs.schedule.app.core.api.lessons.LessonsManager
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoLessonModel
import com.goldenpiedevs.schedule.app.core.notifications.manger.NotificationManager
import com.goldenpiedevs.schedule.app.core.utils.AppPreference
import com.goldenpiedevs.schedule.app.core.utils.NotificationPreference
import com.goldenpiedevs.schedule.app.ui.choose.group.ChooseGroupActivity
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.android.Main
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.indeterminateProgressDialog
import javax.inject.Inject

class ApplicationPreferenceFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var notificationManager: NotificationManager
    @Inject
    lateinit var lessonsManager: LessonsManager

    companion object {
        const val CHANGE_GROUP_CODE = 565
    }

    override fun onCreatePreferences(p0: Bundle?, p1: String?) {
        (activity?.applicationContext as ScheduleApplication).appComponent.inject(this)

        preferenceManager.sharedPreferencesName = getString(R.string.notification_preference_file_name)
        addPreferencesFromResource(R.xml.notification_preference)

        findPreference(getString(R.string.notification_preference_notification_delay_key)).apply {
            summary = "${NotificationPreference.notificationDelay} ${getString(R.string.min)}"

            onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, value ->
                NotificationPreference.notificationDelay = value.toString()
                summary = "${NotificationPreference.notificationDelay} ${getString(R.string.min)}"

                notificationManager.createNotification(DaoLessonModel.getLessonsForGroup(AppPreference.groupId))
                true
            }
        }

        findPreference(getString(R.string.change_group_key)).apply {
            setOnPreferenceClickListener {
                startActivityForResult(Intent(context, ChooseGroupActivity::class.java), CHANGE_GROUP_CODE)
                true
            }
        }

        findPreference(getString(R.string.update_timetable_key)).apply {
            setOnPreferenceClickListener {
                val dialog = activity?.indeterminateProgressDialog("Оновлення розкладу")

                GlobalScope.launch {
                    lessonsManager.loadTimeTable(AppPreference.groupId).await()

                    launch(Dispatchers.Main) {
                        dialog?.dismiss()
                    }
                }
                true
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (resultCode == Activity.RESULT_OK) {
            true -> {
                when (requestCode) {
                    CHANGE_GROUP_CODE -> {
                        activity?.finish()
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}