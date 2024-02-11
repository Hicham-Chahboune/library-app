package com.example.ttss

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ttss.data.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MajorEtud : AppCompatActivity() {
    private var pref: SharedPreferences? = null
    private lateinit var notificationManager: NotificationManager
    private lateinit var modelView: ViewModel

    private val channelId = "i.apps.notifications"
    private val description = "Test notification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_major_etud)
        createNotificationChannel()

        pref = getSharedPreferences("application_preference", MODE_PRIVATE)
        modelView = ViewModelProvider(this).get(ViewModel::class.java)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.koeEt)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host1) as? NavHostFragment
        navHostFragment?.navController?.let {
            setupWithNavController(bottomNavigationView, it)
        }

        // Observe changes and trigger notifications
        modelView.getAllnotificationById(pref!!.getInt("userId", 0)).observe(this, Observer { books ->
            Log.d("NotificationLog", "Notifications to process: ${books.size}")
            GlobalScope.launch {
                val ids: ArrayList<Int> = ArrayList()
                books.forEach { e ->
                    createNotif((0..1500).random(), e.message)
                    ids.add(e.idNotification!!)
                }
                modelView.deleteDataById(ids)
            }
        })
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, description, importance).apply {
                lightColor = Color.GREEN
                enableLights(true)
                enableVibration(true)
            }
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            Log.d("NotificationLog", "Notification channel created")
        }
    }

    fun createNotif(id: Int, message: String) {
        val intentToLaunch = Intent(this, MajorEtud::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intentToLaunch, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val contentView = RemoteViews(packageName, R.layout.notif).apply {
            setTextViewText(R.id.messageNotif, message)
            setImageViewResource(R.id.imageView3, R.drawable.eilco)
        }

        val notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, channelId)
                .setContent(contentView)
                .setSmallIcon(R.drawable.eilco)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.eilco))
                .setContentIntent(pendingIntent)
                .build()
        } else {
            Notification.Builder(this)
                .setContent(contentView)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_background))
                .setContentIntent(pendingIntent)
                .build()
        }

        notificationManager.notify(id, notification)
        Log.d("NotificationLog", "Notification created with ID: $id")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {
            pref?.edit()?.clear()?.apply()
            startActivity(Intent(this, Login::class.java))
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
