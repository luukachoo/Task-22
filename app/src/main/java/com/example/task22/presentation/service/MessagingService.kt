package com.example.task22.presentation.service

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.example.task22.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingService : FirebaseMessagingService() {

    private lateinit var channelId: String

    override fun onCreate() {
        super.onCreate()
        channelId = getString(R.string.channel_id)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        message.notification?.let {
            val id = message.data["id"]?.toIntOrNull() ?: 0
            sendNotification(it.title ?: "Test Title", it.body ?: "Test Body", id)
        }
    }

    private fun sendNotification(title: String, body: String, id: Int) {
        val args = Bundle().also {
            it.putInt("id", id)
        }

        val pendingIntent = NavDeepLinkBuilder(this)
            .setGraph(R.navigation.nav_graph)
            .addDestination(R.id.detailsFragment)
            .setArguments(args)
            .createPendingIntent()

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0, notification.build())
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}
