package com.example.ttss.admin

import ReservationAdapter
import UserReservationAdapter
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.ttss.data.entities.*
import com.example.ttss.data.entities.Favorite
import com.example.ttss.databinding.FragmentBooksBinding
import com.example.ttss.databinding.FragmentMinesBinding
import com.example.ttss.databinding.RowUserBinding
import com.example.ttss.util.DateHandler.Companion.getDateLong
import com.example.ttss.util.GsonHandler

class mines : Fragment() {

    lateinit var viewModel: com.example.ttss.data.ViewModel
    var kw:String="%%"
    lateinit var list:List<BooksWithReservation>
    lateinit var adapter:UserReservationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mines, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(requireActivity()).get(com.example.ttss.data.ViewModel::class.java)

        val binding = FragmentMinesBinding.bind(view)
         adapter = UserReservationAdapter()

        binding.apply {
            rvus.adapter = adapter
            rvus.layoutManager = LinearLayoutManager(requireContext())
            binding.searchetudient.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    if(!s.toString().equals(""))
                        kw = "%${s.toString()}%"
                    else kw="%%"
                    initializeItems()
                }

            })
            binding.generatePdf.setOnClickListener{
                genererPdf();
            }
        }
    }

    fun genererPdf(){

    }

    fun initializeItems(){
        viewModel.getReservationsAverti(kw,getDateLong(0)).observe(this, Observer {
                books->
            adapter.submitList(books)
        })
    }


}