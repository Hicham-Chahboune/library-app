package com.example.ttss


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.ttss.data.ViewModel


class BookEdit : AppCompatActivity() {

    // declaring variables
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Test notification"
    lateinit var modelView: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        modelView = ViewModelProviders.of(this).get(ViewModel::class.java)



        }

    fun createNotif(id:Int,message:String){
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val contentView = RemoteViews(packageName, R.layout.notif)
        contentView.setTextViewText(R.id.messageNotif,message)
        contentView.setImageViewResource(R.id.imageView3,R.drawable.eilco)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, channelId)
                .setContent(contentView)
                .setSmallIcon(R.drawable.eilco)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.eilco))
                .setContentIntent(pendingIntent)
        } else {

            builder = Notification.Builder(this)
                .setContent(contentView)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background))
                .setContentIntent(pendingIntent)
        }
        notificationManager.notify(id, builder.build())
    }

}