package com.example.progettoprogrammazione.ristoratore

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.FragmentAddToMenuBinding
import com.example.progettoprogrammazione.databinding.FragmentCreaMenuBinding
import com.example.progettoprogrammazione.databinding.FragmentCreaRistBinding
import com.example.progettoprogrammazione.models.Product
import com.example.progettoprogrammazione.models.Restaurant
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class FragmentCreaMenu : Fragment() {

    private lateinit var binding: FragmentCreaMenuBinding
    private lateinit var bindingAdd: FragmentAddToMenuBinding
    private var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreaMenuBinding.inflate(layoutInflater)
        bindingAdd = FragmentAddToMenuBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = this.arguments
        val restaurant = args?.getParcelable<Restaurant>("ristorante")
        val restName = restaurant?.idR
        binding.btnBevanda.setOnClickListener {
            addBevanda(restName)
        }
    }

    private fun addBevanda(idR: String?) {
        val inflater = LayoutInflater.from(activity)
        val v = inflater.inflate(R.layout.fragment_add_to_menu, null)
        val addDialog = AlertDialog.Builder(activity)
        var pData: Product
        val nomeP = v.findViewById<EditText>(R.id.nome_prodotto)
        val prezzoP = v.findViewById<EditText>(R.id.prezzo_prodotto)
        val descrizioneP = v.findViewById<EditText>(R.id.descrizione_prodotto)
        val vegan = v.findViewById<CheckBox>(R.id.vegan_newP)

        addDialog.setView(v)
        addDialog.setPositiveButton("OK") { dialog, _ ->
            val nomePbind = nomeP.text.toString()
            val prezzoPbind = prezzoP.text.toString()
            val descrizionePbind = descrizioneP.text.toString()
            val veganPbind = vegan.isChecked
            pData = Product(
                nomePbind,
                prezzoPbind,
                descrizionePbind,
                veganPbind,
                UUID.randomUUID().toString()
            )
            firebaseDatabase.getReference("Ristoranti/$idR/Menu/Bevande").push().setValue(pData)
        }
        addDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }


        addDialog.create()
        addDialog.show()
    }


}