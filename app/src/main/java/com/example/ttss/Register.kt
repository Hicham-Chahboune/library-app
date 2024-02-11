package com.example.ttss

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.ttss.data.ViewModel
import com.example.ttss.data.entities.User
import com.example.ttss.databinding.ActivityRegisterBinding
import com.example.ttss.util.ImageHandler
import java.io.IOException
import java.lang.Exception

class Register : AppCompatActivity() {

    lateinit var binding:ActivityRegisterBinding
    lateinit var viewModel:ViewModel
    lateinit var imageUri:Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register)

        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)

    }
    fun SignupAction(view: View?) {
        binding.imageRegister.buildDrawingCache()
        val bitmap: Bitmap = binding.imageRegister.getDrawingCache()
        val marry: ByteArray = ImageHandler.getBytes(bitmap)
        val user = User(
            null,
            binding.regiserNom.text.toString(),
            binding.registerPrenom.text.toString(),
            binding.spinner.getSelectedItem().toString(),
            binding.registerCin.text.toString(),
            marry,
            binding.registerPassword.text.toString(),
            )
        try {
            viewModel.insertUser(user)
            Toast.makeText(this,"registred",Toast.LENGTH_SHORT).show()
            LoginAction(null)
        } catch (e: Exception) {
            Log.i("registerEx", e.message!!)
        }
    }
    fun ChooseImageAction(view: View?) {
        imageChooser()
    }

    fun imageChooser() {
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(
            Intent.createChooser(i, "Select Picture"),
            1
        )
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 1) {
            val selectedImageUri = data?.data
            if (null != selectedImageUri) {
                imageUri = selectedImageUri
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                    binding.imageRegister.setImageBitmap(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
    fun LoginAction(view: View?) {
        val i = Intent(this, Login::class.java)
        startActivity(i)
    }
}