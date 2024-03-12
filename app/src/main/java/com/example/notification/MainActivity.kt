package com.example.notification

import android.R.drawable.ic_dialog_info
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Icon
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private var notificationManager: NotificationManager? = null
    private var channelID = "com.example.notification"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)

        notificationManager = getSystemService(
            NOTIFICATION_SERVICE
        ) as NotificationManager


        createNotification(
            channelID,
            "Important Notification"
        )


        button.setOnClickListener {
            sendNotification("Example Notification" , "This is an Example")
        }

    }
    fun sendNotification(title: String, content: String) {
        val notificationID = 101
        // Ensure this icon exists in your drawable resources
        val icon: Icon = Icon.createWithResource(this, android.R.drawable.ic_dialog_info)
        val resultIntent = Intent(this, Result::class.java) // Ensure Result activity exists and is registered in AndroidManifest.xml
        val pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT) // Changed to FLAG_UPDATE_CURRENT for testing
        val action: Notification.Action = Notification.Action.Builder(icon, "Open", pendingIntent).build()
        val notification = Notification.Builder(this@MainActivity, channelID)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setChannelId(channelID)
            .setColor(Color.RED)
            .setContentIntent(pendingIntent)
            .addAction(action)
            .build()

        notificationManager?.notify(notificationID,notification)
    }

    fun createNotification(id: String , name: String)
    {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(id , name , importance).apply {
            enableLights(true)
            lightColor = Color.GREEN
            enableVibration(true)
            vibrationPattern = longArrayOf(100 , 200 , 300)

        }

        notificationManager?.createNotificationChannel(channel)



    }


}