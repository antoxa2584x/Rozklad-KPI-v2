package com.goldenpiedevs.schedule.app.core.notifications.manger

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.PowerManager
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.evernote.android.job.JobManager
import com.goldenpiedevs.schedule.app.R
import com.goldenpiedevs.schedule.app.core.dao.timetable.DaoLessonModel
import com.goldenpiedevs.schedule.app.core.dao.timetable.dateFormat
import com.goldenpiedevs.schedule.app.core.dao.timetable.getDayDate
import com.goldenpiedevs.schedule.app.core.notifications.work.ShowNotificationWork
import com.goldenpiedevs.schedule.app.core.utils.AppPreference
import com.goldenpiedevs.schedule.app.core.utils.NotificationPreference
import com.goldenpiedevs.schedule.app.ui.lesson.LessonImplementation.Companion.LESSON_ID
import com.goldenpiedevs.schedule.app.ui.main.MainActivity
import io.realm.Realm
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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

            val realm = Realm.getDefaultInstance()

            for (lesson in lessons) {
                val time = LocalTime.parse(lesson.timeStart, DateTimeFormatter.ofPattern("HH:mm"))
                val date = LocalDate.parse(lesson.getDayDate(), dateFormat)
                val lessonDateTime = LocalDateTime.of(date, time)
                        .minusMinutes(NotificationPreference.notificationDelay.toLong())

                var timeToNotify = ChronoUnit.MILLIS.between(LocalDateTime.now(), lessonDateTime)
                if (timeToNotify < 0)
                    timeToNotify += TimeUnit.DAYS.toMillis(14)

                if (lesson.notificationId != -1)
                    JobManager.instance().cancel(lesson.notificationId)

                lesson.notificationId = ShowNotificationWork.enqueueWork(lesson.id, timeToNotify)
            }

            realm.executeTransaction {
                it.copyToRealmOrUpdate(lessons)
            }

            if (!realm.isClosed)
                realm.close()
        }
    }

    @Suppress("DEPRECATION")
    @SuppressLint("InvalidWakeLockTag")
    fun showNotification(lessonId: String) {
        ShowNotificationWork.enqueueWork(lessonId, TimeUnit.DAYS.toMillis(14)) //repeat notification in 14 days

        val lessonModel = DaoLessonModel.getLesson(lessonId)

        if (!lessonModel.showNotification ||
                lessonModel.groupId != AppPreference.groupId.toString() ||
                !NotificationPreference.showNotification)
            return

        val builder: NotificationCompat.Builder = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationCompat.Builder(context, "notify_001")
        } else {
            NotificationCompat.Builder(context)
        }

        // Setting category
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setCategory(Notification.CATEGORY_ALARM)
        }

        // Creating content intent
        val contentIntent = Intent(context, MainActivity::class.java).putExtra(LESSON_ID, lessonId)

        val startIntent = PendingIntent.getActivity(context, System.currentTimeMillis().toInt(),
                contentIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Adding it to builder
        builder.apply {
            setSmallIcon(R.drawable.kpilogo_shields)
            setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                setVisibility(Notification.VISIBILITY_PUBLIC)
            priority = Notification.PRIORITY_MAX
            setAutoCancel(true)
            setContentIntent(startIntent)
            setContentTitle("${context.getString(R.string.next_lesson)} ${lessonModel.lessonRoom}")

            setStyle(NotificationCompat.BigTextStyle().bigText(lessonModel.lessonName))
            setContentText(lessonModel.lessonName)
        }

        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK
                or PowerManager.ACQUIRE_CAUSES_WAKEUP
                or PowerManager.ON_AFTER_RELEASE, TAG)

        wakeLock?.acquire(500)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("notify_001",
                    context.getString(R.string.app_name),
                    IMPORTANCE_HIGH).apply {
                enableLights(true)
                enableVibration(true)
                lightColor = Color.BLUE
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }

            (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(channel)
        }

        NotificationManagerCompat.from(context)
                .notify(lessonModel.lessonId, builder.build())

        wakeLock?.release()
    }
}