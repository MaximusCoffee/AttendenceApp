package com.example.attendance

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(c: Context, i: Intent) {
        val nm = c.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.createNotificationChannel(
            NotificationChannel("class", "수업 알림", NotificationManager.IMPORTANCE_HIGH)
        )
        val open = PendingIntent.getActivity(
            c, 0, Intent(c, MainActivity::class.java), PendingIntent.FLAG_IMMUTABLE
        )
        val n = Notification.Builder(c, "class")
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle(i.getStringExtra("t"))
            .setContentText(i.getStringExtra("x"))
            .setContentIntent(open)
            .setAutoCancel(true)
            .build()
        nm.notify(i.getIntExtra("id", 0), n)
    }
}
