package com.example.progettoprogrammazione.ristoratore

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.FragmentCreaMenuBinding
import com.example.progettoprogrammazione.databinding.FragmentCreaRistBinding
import com.example.progettoprogrammazione.models.Product
import com.google.firebase.database.FirebaseDatabase

class FragmentCreaMenu : Fragment() {

    private lateinit var binding: FragmentCreaMenuBinding
    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_crea_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBevanda.setOnClickListener {
        //    addBevanda()
        }
    }

    private fun addBevanda(mData: Product, idR: String) {
        val inflater = LayoutInflater.from(activity)
        val v = inflater.inflate(R.layout.fragment_add_to_menu, null)
        val addDialog = AlertDialog.Builder(activity)
        addDialog.setView(v)
               addDialog.setPositiveButton("ok", DialogInterface.OnClickListener{
                       dialog, id-> firebaseDatabase.getReference("Ristoranti/$idR/Menu/Bevande").push().setValue(mData)

              })
        //       addDialog.setNegativeButton("Cancel") { dialog, ->
        //      }


        addDialog.create()
        addDialog.show()
    }


}