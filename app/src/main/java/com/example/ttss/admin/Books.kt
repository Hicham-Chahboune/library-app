package com.example.ttss.admin

import android.content.Context.MODE_PRIVATE
import android.content.Intent
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
import com.example.ttss.util.GsonHandler
import android.content.SharedPreferences
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class Books : Fragment(),UserAdapter.OnItemClickListener {

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
        viewModel.allBooksWithFavorites(pref!!.getInt("userId",0),"%%").observe(this, Observer {
                books->
            adapter.submitList(books)
        })


        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                MaterialAlertDialogBuilder(view.context).setTitle("alert").setMessage("This book will be deleted")
                    .setNegativeButton("Cancel"){e,b->}
                    .setPositiveButton("Submit"){
                        b,x->
                        viewModel.deleteBook(adapter.getDataAt(viewHolder.getAdapterPosition()).book);
                    }.show()
            }
        }).attachToRecyclerView(binding.frgmrv)
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
        b.putInt("idcat",booksWithFavorite.categorie?.idCategorie!!)
        b.putString("description", booksWithFavorite.book.description.toString())
        b.putByteArray("image", booksWithFavorite.book.bookImage)
        i.putExtras(b)
        startActivity(i)
    }

    override fun onFavorite(view: RowUserBinding, booksWithFavorite: BooksWithFavorite) {
        when(view.hearth.tag){
            "black"->{
                view.hearth.setTag("red")
                view.hearth.setImageResource(R.drawable.redfavorite)
                viewModel.addFavorite(com.example.ttss.data.entities.Favorite(booksWithFavorite.book.bookId!!,pref!!.getInt("userId",0)))
            }
            "red"->{
                view.hearth.setTag("black")
                view.hearth.setImageResource(R.drawable.ic_baseline_favorite_24)
                viewModel.deletaFavorite(com.example.ttss.data.entities.Favorite(booksWithFavorite.book.bookId!!,pref!!.getInt("userId",0)))
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