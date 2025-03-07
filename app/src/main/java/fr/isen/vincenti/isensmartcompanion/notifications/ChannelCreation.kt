package fr.isen.vincenti.isensmartcompanion.notifications

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import android.util.Log
import fr.isen.vincenti.isensmartcompanion.R

const val CHANNEL_ID = "event_notifications_channel"

fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = context.getString(R.string.event_notifications)
        val descriptionText = context.getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

@SuppressLint("ScheduleExactAlarm")
fun scheduleNotification(context: Context, title: String, date: String, location: String, category: String) {
    val intent = Intent(context, NotificationReceiver::class.java).apply {
        putExtra("title", title)
        putExtra("date", date)
        putExtra("location", location)
        putExtra("category", category)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        title.hashCode(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val triggerTime = SystemClock.elapsedRealtime() + 10000

    alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pendingIntent)
}

