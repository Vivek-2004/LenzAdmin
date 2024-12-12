package com.fitting.lenz

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ForegroundService : Service() {

    private val CHANNEL_ID = "LENZ"
    private var serviceJob: Job? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val foregroundNotification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Service Running")
            .setContentText("Checking for notifications in the background")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        startForeground(1, foregroundNotification)

        startBackgroundTask()

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob?.cancel()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    private fun startBackgroundTask() {
        serviceJob = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                checkForNotifications()
                delay(5000L)
            }
        }
    }

    private fun showNotifications(
        title: String,
        message: String
    ) {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .build()

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    fun checkForNotifications() {
        CoroutineScope(Dispatchers.IO).launch {
            val lenz = lenzService.getTest()
            val show = lenz.first().notification
            if(show) {
                val title = "New Update"
                val message = "$show  ${System.currentTimeMillis()}"
                showNotifications(
                    title = title,
                    message = message
                )
            }
        }
    }
}