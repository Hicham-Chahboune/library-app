package com.example.ttss

import android.content.Intent
import android.content.SharedPreferences
import android.media.Image
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.ttss.data.entities.Reservation
import com.example.ttss.data.entities.User
import com.example.ttss.databinding.ActivityBookDetailsBinding
import com.example.ttss.databinding.ActivityUserDetailsBinding
import com.example.ttss.util.GsonHandler
import com.example.ttss.util.ImageHandler

class BookDetails : AppCompatActivity() {

    lateinit var viewModel: com.example.ttss.data.ViewModel
    private lateinit var binding: ActivityBookDetailsBinding
    var pref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_book_details)
        pref = applicationContext.getSharedPreferences("application_preference", MODE_PRIVATE)

        viewModel= ViewModelProvider(this).get(com.example.ttss.data.ViewModel::class.java)

        val bundle = intent.extras


        val title:String = bundle!!.getString("title","")
        val auteur:String = bundle!!.getString("auteur","")
        val description:String = bundle!!.getString("description","")
        val image:ByteArray = bundle.getByteArray("image")!!
        val pages:String = bundle.getString("pages")!!
        val idBook:Int = bundle.getInt("id",0)
        val type:String = bundle.getString("type","")
        val categorie:String =bundle.getString("categorie","")
        val userJson:String = bundle.getString("user","")


        if(!type.equals("")){
            binding.reserver.visibility= View.VISIBLE
        }

        val rv:Reservation? = viewModel.getReservation(pref!!.getInt("userId",0),idBook )

        if(rv==null  || rv?.isReturned==true){
            binding.reserver.setEnabled(true);
        }else{
            binding.reserver.setEnabled(false);
        }

        binding.reserver.setOnClickListener {
            val reservation = Reservation(idBook, pref!!.getInt("userId",0))
            viewModel.addReservation(reservation)
            binding.reserver.setEnabled(false)
        }


        binding.titledetails.text="title: ${title}"
        binding.auteurdetails.text="auteur: ${auteur}"
        binding.descriptiondetails.text="description: ${description}"
        binding.detailscategorie.text="Categorie: ${categorie}"
        binding.detailspages.text="pages: ${pages}"
        binding.imgdetails.setImageBitmap(ImageHandler.getImage(image))

        if(!userJson.equals("")){
            var user:User = GsonHandler.getGsonParser()!!.fromJson(userJson,User::class.java)
            binding.userReserve.visibility=View.VISIBLE
            binding.userReserve.text="Reserver par ${user.nom} ${user.prenom.toUpperCase()}"

            binding.userReserve.setOnClickListener {
                val i = Intent(this, UserDetails::class.java)
                val b = Bundle()
                b.putString("user",userJson)
                i.putExtras(b)
                startActivity(i)
            }
        }


    }
}