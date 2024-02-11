package com.example.ttss.admin

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ttss.AjouterLivre
import com.example.ttss.BookDetails
import com.example.ttss.R
import com.example.ttss.adapters.UserAdapter
import com.example.ttss.data.entities.BooksWithFavorite
import com.example.ttss.data.entities.Categorie
import com.example.ttss.data.entities.Favorite
import com.example.ttss.data.entities.User
import com.example.ttss.databinding.FragmentBooksBinding
import com.example.ttss.databinding.RowUserBinding

class Favorite : Fragment(),UserAdapter.OnItemClickListener {

    lateinit var viewModel: com.example.ttss.data.ViewModel
    var pref: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_books, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(requireActivity()).get(com.example.ttss.data.ViewModel::class.java)
        pref = view.getContext().getSharedPreferences("application_preference", MODE_PRIVATE);

        val binding = FragmentBooksBinding.bind(view)
        val adapter = UserAdapter()

        adapter.setmOnItem(this)

        binding.apply {
            frgmrv.adapter = adapter
            frgmrv.layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.getAllFavorites(pref!!.getInt("userId",0)).observe(this, Observer {
                books->
            adapter.submitList(books)
            Log.i("iohdfvoivf","${books.size}")
        })
    }

    override fun onItemClick(view: RowUserBinding, booksWithFavorite: BooksWithFavorite) {

    }

    override fun onItemLongClick(view: RowUserBinding, booksWithFavorite: BooksWithFavorite) {
        val i = Intent(context, AjouterLivre::class.java)
        val b = Bundle()
        b.putString("title", booksWithFavorite.book.title)
        b.putString("auteur", booksWithFavorite.book.auteur)
        b.putString("pages", booksWithFavorite.book.nombrePages.toString())
        b.putInt("id", booksWithFavorite.book.bookId!!)
        b.putByteArray("image", booksWithFavorite.book.bookImage)
        i.putExtras(b)
        startActivity(i)
    }

    override fun onFavorite(view: RowUserBinding, booksWithFavorite: BooksWithFavorite) {
        when(view.hearth.tag){
            "black"->{
                view.hearth.setTag("red")
                view.hearth.setImageResource(R.drawable.redfavorite)
                viewModel.addFavorite(com.example.ttss.data.entities.Favorite(booksWithFavorite.book.bookId!!,pref!!.getInt("userId",0),))
            }
            "red"->{
                view.hearth.setTag("black")
                view.hearth.setImageResource(R.drawable.ic_baseline_favorite_24)
                viewModel.deletaFavorite(com.example.ttss.data.entities.Favorite(booksWithFavorite.book.bookId!!,pref!!.getInt("userId",0),))
            }
        }
    }

    override fun onDetailsClick(booksWithFavorite: BooksWithFavorite) {
        val i = Intent(context, BookDetails::class.java)
        val b = Bundle()
        b.putString("categorie",booksWithFavorite.categorie?.categorieDesignation)
        b.putString("title", booksWithFavorite.book.title)
        b.putString("auteur", booksWithFavorite.book.auteur)
        b.putString("pages", booksWithFavorite.book.nombrePages.toString())
        b.putString("description", booksWithFavorite.book.description.toString())
        b.putInt("id", booksWithFavorite.book.bookId!!)
        b.putByteArray("image", booksWithFavorite.book.bookImage)
        i.putExtras(b)
        startActivity(i)
    }


}