package com.example.ttss.admin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ttss.R
import com.example.ttss.data.entities.*
import com.example.ttss.data.entities.Reservation
import com.example.ttss.databinding.FragmentReservationBinding
import ReservationAdapter
import android.content.Intent
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.example.ttss.BookDetails
import com.example.ttss.util.GsonHandler
import kotlinx.android.synthetic.main.rv_row.*
import java.lang.Exception


class Reservation : Fragment(),ReservationAdapter.OnItemClickListener {

    lateinit var viewModel: com.example.ttss.data.ViewModel
    var reserved=false
    var taken=false
    var kw:String="%%"

    lateinit var adapter:ReservationAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reservation, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel= ViewModelProvider(requireActivity()).get(com.example.ttss.data.ViewModel::class.java)
        val binding = FragmentReservationBinding.bind(view)
        adapter = ReservationAdapter()
        adapter.setmOnItem(this)

        binding.apply {
            rvrv.adapter = adapter
            rvrv.layoutManager = LinearLayoutManager(requireContext())

                spinner2.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            selectedItem(position)
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {}
                    }

            searchreservation.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    if(!s.toString().equals(""))
                    kw = "%${s.toString()}%"
                    initializeItems()
                }

            })
        }

    }
    private fun selectedItem(position: Int) {
        when (position) {
            0->{
                reserved=false
                taken=false
            }
            1 -> {
                reserved = true
                taken=false
            }
            2 -> {
                reserved = true
                taken = true
            }
        }
        initializeItems()
    }

    fun initializeItems(){
        viewModel.getReservations(reserved,taken,kw).observe(this, Observer {
                books->
            adapter.submitList(books)
        })
    }

    override fun onAcceptClick(booksWithReservation: BooksWithReservation) {
        var reservation : Reservation =booksWithReservation.reservation
        if(reservation.isTaken){
            reservation.isReturned=true
            reservation.dateReturn=System.currentTimeMillis()
            viewModel.insertNotification(Notification(null,booksWithReservation.user?.idUser!!,"returned book : ${booksWithReservation.book?.title}"))

        }
        else if(reservation.isReserved){
            reservation.isTaken=true
            reservation.dateReservation=System.currentTimeMillis()
            viewModel.insertNotification(Notification(null,booksWithReservation.user?.idUser!!,"vous avez pris le livre ${booksWithReservation.book?.title} vous n'avez que 7 jours pour le rendre"))
        }
        else{
            reservation.isReserved=true
            viewModel.insertNotification(Notification(null,booksWithReservation.user?.idUser!!,"La reservation a été effectuée pour ${booksWithReservation.book?.title}"))
        }
        viewModel.addReservation(reservation)
    }

    override fun onRejetClick(booksWithReservation: BooksWithReservation) {
        var reservation : Reservation =booksWithReservation.reservation
        if(!reservation.isReserved){
            viewModel.insertNotification(Notification(null,booksWithReservation.user?.idUser!!,"La reservation n'a pas été effectuée pour le livre : ${booksWithReservation.book?.title}"))
            viewModel.deleteReservation(reservation)
            Toast.makeText(requireContext(),"Reservation deleted",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onLongClick(booksWithReservation: BooksWithReservation) {
        val i = Intent(context, BookDetails::class.java)
        val b = Bundle()
        b.putString("title", booksWithReservation.book?.title)
        b.putString("auteur", booksWithReservation.book?.auteur)
        b.putString("pages", booksWithReservation.book?.nombrePages.toString())
        b.putString("description", booksWithReservation.book?.description.toString())
        b.putInt("id", booksWithReservation.book?.bookId!!)
        b.putByteArray("image", booksWithReservation!!.book?.bookImage)
        val userJson: String = GsonHandler.getGsonParser()!!.toJson(booksWithReservation.user)
        b.putString("user",userJson)
        i.putExtras(b)
        startActivity(i)
    }


}