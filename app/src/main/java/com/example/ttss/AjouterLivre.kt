package com.example.ttss

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.ttss.data.ViewModel
import com.example.ttss.data.entities.Book
import com.example.ttss.databinding.ActivityAjouterLivreBinding
import com.example.ttss.util.ImageHandler
import java.io.IOException
import java.lang.Exception

class AjouterLivre : AppCompatActivity() {

    lateinit var viewModel:ViewModel
    var id:Int?=null
    lateinit var imageUri: Uri
    lateinit var binding: ActivityAjouterLivreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_ajouter_livre)

        viewModel= ViewModelProvider(this).get(com.example.ttss.data.ViewModel::class.java)
        title="Add new book"

        val bundle = intent.extras
        val array:ArrayList<String> = ArrayList()
        val list = viewModel.getCategories().forEach(){it->
            array.add(it.categorieDesignation)
        }

        binding.spadd.adapter= ArrayAdapter(this,android.R.layout.simple_spinner_item,array)

        try {
            id = bundle!!.getInt("id")
            binding.edAuteur.setText(bundle.getString("auteur"))
            binding.edTitle.setText(bundle.getString("title"))
            binding.nbPages.setText(bundle.getString("pages"))
            val array: ByteArray? = bundle.getByteArray("image")
            binding.nbDescription.setText(bundle.getString("description"))
            binding.spadd.setSelection(bundle!!.getInt("idcat")-1)
            binding.imgBook.setImageBitmap(ImageHandler.getImage(array!!))
            title = "Edit Book"
        } catch (e: Exception) {
            Log.i("myError", e.toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.save_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_note -> {
                this.saveNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveNote() {
        binding.imgBook.buildDrawingCache()
        val bitmap: Bitmap = findViewById<ImageView>(R.id.img_book).getDrawingCache()
        val marry: ByteArray = ImageHandler.getBytes(bitmap)
        val idCategory = viewModel.getCatoriesEquals(binding.spadd.selectedItem as String).idCategorie
        val w:Book = Book(
            id,
            binding.edTitle.text.toString(),
            idCategory,
            binding.edAuteur.text.toString(),
            binding.nbPages.text.toString().toInt(),
            marry,
            binding.nbDescription.text.toString()
        )

        this.viewModel.insertBook(w)
        finish()
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
                    findViewById<ImageView>(R.id.img_book).setImageBitmap(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun ChooseImageAction(view: View) {
        imageChooser()
    }
}