import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ttss.R
import com.example.ttss.data.entities.BooksWithFavorite
import com.example.ttss.data.entities.BooksWithReservation
import com.example.ttss.databinding.RowUserBinding
import com.example.ttss.databinding.RvRowBinding
import com.example.ttss.util.ImageHandler

class ReservationAdapter: ListAdapter<BooksWithReservation, ReservationAdapter.bookViewHolder>(ReservationAdapter.diffCallback) {

    var mOnItemClickListener: OnItemClickListener? = null

    fun setmOnItem(mOnItemClickListener: OnItemClickListener){
        this.mOnItemClickListener =mOnItemClickListener
    }


    inner class bookViewHolder(private val binding:RvRowBinding ) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(booksWithReservation: BooksWithReservation){

            binding.root.setOnLongClickListener {
                mOnItemClickListener?.onLongClick(booksWithReservation)
                true
            }
            binding.apply {
                rvtxtTitle.text=booksWithReservation.book?.title
                rvtxtAuteur.text=booksWithReservation.book?.auteur
                rvtxtPage.text="${booksWithReservation.book?.nombrePages}"
                rvimage.setImageBitmap(ImageHandler.getImage(booksWithReservation.book!!.bookImage))
                valider.setOnClickListener{
                    mOnItemClickListener?.onAcceptClick(booksWithReservation)
                }
                rejeter.setOnClickListener{
                    mOnItemClickListener?.onRejetClick(booksWithReservation)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bookViewHolder {
        val binding = RvRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return bookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: bookViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    interface OnItemClickListener {
        fun onAcceptClick( booksWithReservation: BooksWithReservation)
        fun onRejetClick( booksWithReservation: BooksWithReservation)
        fun onLongClick(booksWithReservation: BooksWithReservation)

    }
    companion object{

        val diffCallback: DiffUtil.ItemCallback<BooksWithReservation> =
            object : DiffUtil.ItemCallback<BooksWithReservation>() {
                override fun areItemsTheSame(oldItem: BooksWithReservation, newItem: BooksWithReservation): Boolean {
                    return oldItem.book?.bookId === newItem.book?.bookId
                }

                override fun areContentsTheSame(oldItem: BooksWithReservation, newItem: BooksWithReservation): Boolean {
                    return oldItem.book?.auteur.equals(newItem.book?.auteur) &&
                            oldItem.book?.title.equals(newItem.book?.title)
                }
            }
    }

}