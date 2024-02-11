package com.example.ttss

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.ttss.data.entities.User
import com.example.ttss.databinding.ActivityUserDetailsBinding
import com.example.ttss.util.GsonHandler
import com.example.ttss.util.ImageHandler

class UserDetails : AppCompatActivity() {

    private lateinit var binding:ActivityUserDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=DataBindingUtil.setContentView(this,R.layout.activity_user_details)

        val bundle = intent.extras
        val userJson:String = bundle!!.getString("user","")

        var user: User = GsonHandler.getGsonParser()!!.fromJson(userJson, User::class.java)


        binding.cinuser.text ="cin : ${user.cin}"
        binding.nomuser.text="nom : ${user.nom}"
        binding.prenomuser.text="prenom : ${user.prenom}"
        binding.userimage.setImageBitmap(ImageHandler.getImage(user.image!!))



    }
}