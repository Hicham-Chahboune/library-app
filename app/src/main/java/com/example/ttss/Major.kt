package com.example.ttss

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_major.view.*

class Major : AppCompatActivity() {

    var pref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_major)

        pref = applicationContext.getSharedPreferences("application_preference", MODE_PRIVATE)
        title=pref!!.getString("name","Prof")
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.koe)
        val navHostFragment =supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment?
       setupWithNavController(bottomNavigationView, navHostFragment!!.navController)

        val floatingActionButton = findViewById<FloatingActionButton>(R.id.bottomplus)

        floatingActionButton.setOnClickListener {
            val i = Intent(this@Major, AjouterLivre::class.java)
            startActivityForResult(i, 1)
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.appmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {
            pref?.edit()!!.clear().commit()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
        return true
    }

}
