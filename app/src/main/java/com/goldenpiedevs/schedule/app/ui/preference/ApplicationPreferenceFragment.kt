package com.goldenpiedevs.schedule.app.ui.preference

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.os.Handler
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.ScheduleApplication
import com.goldenpiedevs.schedule.app.core.api.lessons.LessonsManager
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoLessonModel
import com.goldenpiedevs.schedule.app.core.notifications.manger.NotificationManager
import com.goldenpiedevs.schedule.app.core.utils.preference.AppPreference
import com.goldenpiedevs.schedule.app.core.utils.preference.UserPreference
import com.goldenpiedevs.schedule.app.ui.choose.group.ChooseGroupActivity
import com.goldenpiedevs.schedule.app.ui.main.MainActivity
import com.goldenpiedevs.schedule.app.ui.widget.ScheduleWidgetProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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

        preferenceManager.sharedPreferencesName = getString(R.string.user_preference_file_name)
        addPreferencesFromResource(R.xml.app_preference)

        findPreference(getString(R.string.user_preference_notification_delay_key)).apply {
            summary = "${UserPreference.notificationDelay} ${getString(R.string.min)}"

            onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, value ->
                UserPreference.notificationDelay = value.toString()
                summary = "${UserPreference.notificationDelay} ${getString(R.string.min)}"

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

        findPreference(getString(R.string.user_preference_reverse_week_key)).apply {
            onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, value ->
                UserPreference.reverseWeek = value.toString().toBoolean()

                Handler().postDelayed({
                    ScheduleWidgetProvider.updateWidget(context)

                    val intent = Intent(context, MainActivity::class.java)
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                    if (context is Activity) {
                        (context as Activity).finish()
                    }

                    Runtime.getRuntime().exit(0)
                }, 200)


                true
            }
        }

        findPreference(getString(R.string.update_timetable_key)).apply {
            setOnPreferenceClickListener {
                val dialog = activity?.indeterminateProgressDialog("Оновлення розкладу")

                GlobalScope.launch {
                    lessonsManager.loadTimeTableAsync(AppPreference.groupId).await()

                    launch(Dispatchers.Main) {
                        dialog?.dismiss()
                        ScheduleWidgetProvider.updateWidget(context)
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