package fr.isen.vincenti.isensmartcompanion.notifications

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import fr.isen.vincenti.isensmartcompanion.R

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        val title = intent.getStringExtra("title") ?: context.getString(R.string.event_default_title)
        val date = intent.getStringExtra("date") ?: context.getString(R.string.unknown_date)
        val location = intent.getStringExtra("location") ?: context.getString(R.string.unknown_location)
        val category = intent.getStringExtra("category") ?: context.getString(R.string.no_category)

        val CHANNEL_ID = "event_notifications_channel"

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo_min)
            .setContentTitle(context.getString(R.string.notification_title, title))
            .setContentText(context.getString(R.string.notification_content, date, location, category))
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(context.getString(R.string.notification_big_text, title, date, location))
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(1, builder.build())
        }
    }
}
