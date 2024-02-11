import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ttss.R
import com.example.ttss.data.entities.BooksWithFavorite
import com.example.ttss.data.entities.BooksWithReservation
import com.example.ttss.databinding.RowReservationUserBinding
import com.example.ttss.databinding.RowUserBinding
import com.example.ttss.databinding.RvRowBinding
import com.example.ttss.util.ImageHandler

class UserReservationAdapter: ListAdapter<BooksWithReservation, UserReservationAdapter.bookViewHolder>(UserReservationAdapter.diffCallback) {

    var mOnItemClickListener: OnItemClickListener? = null

    fun setmOnItem(mOnItemClickListener: OnItemClickListener){
        this.mOnItemClickListener =mOnItemClickListener
    }


    inner class bookViewHolder(private val binding:RowReservationUserBinding ) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(booksWithReservation: BooksWithReservation){

            binding.apply {
                rvusimage.setImageBitmap(ImageHandler.getImage(booksWithReservation.book!!.bookImage))
                rvustxtNom.text=booksWithReservation.user?.nom
                rvustxtPrenom.text=booksWithReservation.user?.prenom
                rvtxtCin.text=booksWithReservation.user?.cin

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bookViewHolder {
        val binding = RowReservationUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
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