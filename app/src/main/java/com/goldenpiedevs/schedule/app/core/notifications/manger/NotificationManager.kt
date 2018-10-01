package com.goldenpiedevs.schedule.app.core.notifications.manger

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoLessonModel
import com.goldenpiedevs.schedule.app.core.dao.timetable.getDayDate
import com.goldenpiedevs.schedule.app.core.notifications.work.ShowNotificationWork
import com.goldenpiedevs.schedule.app.core.utils.NotificationPreference
import com.goldenpiedevs.schedule.app.ui.main.MainActivity
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import java.util.concurrent.TimeUnit


class NotificationManager(private val context: Context) {

    companion object {
        const val TAG = "notification_job"
    }

    fun createNotification(lessons: List<DaoLessonModel>) {
        GlobalScope.launch {
            for (lesson in lessons) {
                val time = LocalTime.parse(lesson.timeStart, DateTimeFormatter.ofPattern("HH:mm"))
                val date = LocalDate.parse(lesson.getDayDate())
                val lessonDateTime = LocalDateTime.of(date, time)
                lessonDateTime.minusMinutes(NotificationPreference.notificationDelay.toLong())

                ShowNotificationWork.enqueueWork(lesson.id, ChronoUnit.MILLIS.between(LocalDateTime.now(), lessonDateTime))
            }
        }
    }

    @SuppressLint("InvalidWakeLockTag")
    fun showNotification(lessonId: String) {
        ShowNotificationWork.enqueueWork(lessonId, TimeUnit.DAYS.toMillis(14)) //repeat notification in 14 days

        val lessonModel = DaoLessonModel.getLesson(lessonId)

        if (!lessonModel.showNotification || !NotificationPreference.showNotification)
            return

        val builder: NotificationCompat.Builder = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationCompat.Builder(context, NotificationChannel.DEFAULT_CHANNEL_ID)
        } else {
            NotificationCompat.Builder(context)
        }

        // Setting category
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setCategory(Notification.CATEGORY_ALARM)
        }

        //TODO Add icons

        val wearableExtender = NotificationCompat.WearableExtender()
//        wearableExtender.background = BitmapFactory.decodeResource(context.resources,
//                R.drawable.wear_notification_background)
        //TODO Add wear splash

        // Creating content intent
        val contentIntent = Intent(context, MainActivity::class.java)
//            contentIntent.putExtra(CONFIRMATION_UUID, lesson.id())
        //TODO Add static filed

        val startIntent = PendingIntent.getActivity(context, System.currentTimeMillis().toInt(),
                contentIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Adding it to builder
        builder.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                setVisibility(Notification.VISIBILITY_PUBLIC)
            priority = Notification.PRIORITY_MAX
            setAutoCancel(true)
            extend(wearableExtender)
            setContentIntent(startIntent)
            setContentTitle(context.getString(R.string.next_lesson))
            setContentText(lessonModel.lessonFullName)
            setSubText(lessonModel.lessonRoom)
        }

        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK
                or PowerManager.ACQUIRE_CAUSES_WAKEUP
                or PowerManager.ON_AFTER_RELEASE, TAG)

        wakeLock?.acquire(500)

        NotificationManagerCompat.from(context)
                .notify(lessonModel.lessonId, builder.build())

        wakeLock?.release()
    }
}