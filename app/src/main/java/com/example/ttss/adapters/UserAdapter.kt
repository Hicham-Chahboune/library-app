package com.example.ttss.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ttss.R
import com.example.ttss.databinding.RowUserBinding
import com.example.ttss.util.ImageHandler
import com.example.ttss.data.entities.BooksWithFavorite as BooksWithFavorite

class UserAdapter:ListAdapter<BooksWithFavorite,UserAdapter.bookViewHolder>(UserAdapter.diffCallback) {

    var mOnItemClickListener: OnItemClickListener? = null

    fun setmOnItem(mOnItemClickListener: OnItemClickListener){
        this.mOnItemClickListener =mOnItemClickListener
    }
    fun getDataAt(p:Int):BooksWithFavorite = getItem(p)
    inner class bookViewHolder(private val binding:RowUserBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(booksWithFavorite: BooksWithFavorite){
            binding.root.setOnLongClickListener{
                mOnItemClickListener?.onItemLongClick(binding,booksWithFavorite)
                true
            }
            binding.apply {
                rvtxtTitle.text=booksWithFavorite.book.title
                rvtxtAuteur.text=booksWithFavorite.book.auteur
                rvtxtPage.text="${booksWithFavorite.book.nombrePages}"
                rvimage.setImageBitmap(ImageHandler.getImage(booksWithFavorite.book.bookImage))
                rvtxtDetails.setOnClickListener{
                    mOnItemClickListener?.onDetailsClick(booksWithFavorite)
                }
                if (booksWithFavorite.favorite != null) {
                    hearth.setImageResource(R.drawable.redfavorite)
                    hearth.setTag("red")
                } else{
                    hearth.setImageResource(R.drawable.ic_baseline_favorite_24)
                    hearth.setTag("black")
                }

                hearth.setOnClickListener{
                    mOnItemClickListener?.onFavorite(binding,booksWithFavorite)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bookViewHolder {
        val binding = RowUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return bookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: bookViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    interface OnItemClickListener {
        fun onItemClick(view: RowUserBinding, position: BooksWithFavorite)
        fun onItemLongClick(view: RowUserBinding, position: BooksWithFavorite)
        fun onFavorite(view: RowUserBinding, position: BooksWithFavorite)
        fun onDetailsClick(booksWithFavorite: BooksWithFavorite)
    }
    companion object{

        val diffCallback: DiffUtil.ItemCallback<BooksWithFavorite> =
            object : DiffUtil.ItemCallback<BooksWithFavorite>() {
                override fun areItemsTheSame(oldItem: BooksWithFavorite, newItem: BooksWithFavorite): Boolean {
                    return oldItem.book.bookId === newItem.book.bookId
                }

                override fun areContentsTheSame(oldItem: BooksWithFavorite, newItem: BooksWithFavorite): Boolean {
                    return oldItem.book.auteur.equals(newItem.book.auteur) &&
                            oldItem.book.title.equals(newItem.book.title)
                }
            }
    }

}