package com.goldenpiedevs.schedule.app.ui.preference

import android.os.Bundle
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.utils.NotificationPreference

class NotificationPreferenceFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(p0: Bundle?, p1: String?) {
        preferenceManager.sharedPreferencesName = getString(R.string.notification_preference_file_name)
        addPreferencesFromResource(R.xml.notification_preference)

        findPreference(getString(R.string.notification_preference_notification_delay_key)).apply {
            summary = "${NotificationPreference.notificationDelay} ${getString(R.string.min)}"

            onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, value ->
                NotificationPreference.notificationDelay = value.toString().trim().toInt()
                summary = "${NotificationPreference.notificationDelay} ${getString(R.string.min)}"

                true
            }
        }
    }
}