package com.example.progettoprogrammazione.ristoratore

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.FragmentAddToMenuBinding
import com.example.progettoprogrammazione.databinding.FragmentCreaMenuBinding
import com.example.progettoprogrammazione.models.Product
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.models.User
import com.example.progettoprogrammazione.utils.ProductUtils
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class FragmentCreaMenu : Fragment(), ProductUtils {

    private lateinit var binding: FragmentCreaMenuBinding
    private lateinit var bindingAdd: FragmentAddToMenuBinding

    private lateinit var user: User

    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreaMenuBinding.inflate(layoutInflater)
        bindingAdd = FragmentAddToMenuBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = this.arguments
        val restaurant = args?.getParcelable<Restaurant>("ristorante")
        user = args?.getParcelable<User>("user") as User
        val restName = restaurant?.idR

        binding.btnBevanda.setOnClickListener {
            showDialog("bevanda", restName)
        }

        binding.btnAntipasto.setOnClickListener {
            showDialog("antipasto", restName)
        }

        binding.btnPrimo.setOnClickListener {
            showDialog("primo", restName)
        }

        binding.btnSecondo.setOnClickListener {
            showDialog("secondo", restName)
        }

        binding.btnContorno.setOnClickListener {
            showDialog("contorno", restName)
        }

        binding.btnDolce.setOnClickListener {
            showDialog("dolce", restName)
        }

        binding.constraintfine.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("user", user)
            view.findNavController().navigate(R.id.Gestione_R, bundle)
        }
    }

    private fun showDialog(add: String, restName: String?) {
        val inflater = LayoutInflater.from(activity)
        val v = inflater.inflate(R.layout.fragment_add_to_menu, null)
        val addDialog = AlertDialog.Builder(activity)
        val nomeP = v.findViewById<EditText>(R.id.nome_prodotto)
        val prezzoP = v.findViewById<EditText>(R.id.prezzo_prodotto)
        val descrizioneP = v.findViewById<EditText>(R.id.descrizione_prodotto)

        addDialog.setView(v)
        addDialog.setPositiveButton("OK") { _, _ ->
            val nomePbind = nomeP.text.toString()
            val prezzoPbind = prezzoP.text.toString()
            val descrizionePbind = descrizioneP.text.toString()
            val pData = Product(
                nomePbind,
                prezzoPbind,
                descrizionePbind,
                UUID.randomUUID().toString()
            )
            when (add) {
                "bevanda" -> addBevanda(restName, pData, context)
                "antipasto" -> addAntipasto(restName, pData, context)
                "primo" -> addPrimo(restName, pData, context)
                "secondo" -> addSecondo(restName, pData, context)
                "contorno" -> addContorno(restName, pData, context)
                "dolce" -> addDolce(restName, pData, context)
            }
        }
        addDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        addDialog.create()
        addDialog.show()
    }

}