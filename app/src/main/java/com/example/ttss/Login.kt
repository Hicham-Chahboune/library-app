package com.example.ttss

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.ttss.data.ViewModel
import com.example.ttss.data.api.ApiClient
import com.example.ttss.data.entities.Book
import com.example.ttss.data.entities.User
import com.example.ttss.databinding.ActivityLoginBinding
import com.example.ttss.util.ROLESELECT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class Login : AppCompatActivity() {
    lateinit var pref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var viewModel:ViewModel
    lateinit var binding:ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_login)

        pref = applicationContext.getSharedPreferences("application_preference", MODE_PRIVATE)
        editor = pref.edit()


        val userFirstLogin = pref.getBoolean("auth", false)
        if (userFirstLogin) {


            val role = pref.getString("role", "")
            if (role == ROLESELECT.PROF) {
                val i = Intent(this, Major::class.java)
                startActivity(i)
            } else {
                val i = Intent(this, MajorEtud::class.java)
                startActivity(i)
            }
            finish()
        }
        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)

    }

    fun loginHandler(view: View?) {
        val cin: String = binding.loginCin.text.toString()
        val pass: String = binding.loginPassword.text.toString()
        val user: User? = viewModel.getByCin(cin)

        if (user != null && pass == user.password) {
            editor.putBoolean("auth", true) // Saving integer
            editor.putInt("userId", user.idUser!!)
            editor.putString("role", user.Role)
            editor.putString("name", "${user.nom} ${user.prenom}")// Saving float
            editor.apply()
            if (user.Role.equals(ROLESELECT.PROF)) {
                val i = Intent(this, Major::class.java)
                startActivity(i)
            } else {
                val i = Intent(this, MajorEtud::class.java)
                startActivity(i)
            }
            finish()
        } else Toast.makeText(this, "not logged", Toast.LENGTH_SHORT).show()
    }
    fun RegisterAction(view: View?) {
        val i = Intent(this, Register::class.java)
        startActivity(i)
    }


}