package com.example.progettoprogrammazione.ristoratore

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.ActivityCreaMenuBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackRestaurant
import com.example.progettoprogrammazione.models.Product
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.models.User
import com.example.progettoprogrammazione.utils.ProductUtils
import com.example.progettoprogrammazione.utils.ResponseRistorante
import com.example.progettoprogrammazione.utils.RestaurantUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Questo fragment permette all'utente di creare un nuovo menu, aggiungendo prodotti e relative informazioni

class FragmentCreaMenu : Fragment(), ProductUtils, RestaurantUtils {

    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var binding: ActivityCreaMenuBinding

    private lateinit var user: User
    private lateinit var ristorante: Restaurant

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ActivityCreaMenuBinding.inflate(layoutInflater)


        val args = this.arguments
        user = args?.getParcelable<User>("user") as User
        ristorante = args.getParcelable<Restaurant>("ristorante") as Restaurant
        val restName = ristorante.idR

        // Queste funzioni aggiungono al menu il prodotto inerente alla tipologia passata come parametro
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

        // Cliccando sul bottone, si finisce di creare il menu e si torna alla pagina Ristoranti
        binding.constraintfine.setOnClickListener {
            getRestaurantData(object : FireBaseCallbackRestaurant {
                override fun onResponse(responseR: ResponseRistorante) {
                    val bundle = Bundle()
                    bundle.putParcelable("user", user)
                    bundle.putParcelableArrayList("ristoranti", responseR.ristoranti)
                    view?.findNavController()?.navigate(R.id.CreaMenuToRistoranti, bundle)
                }
            }, context)
        }

        return binding.root
    }

    // Questa funzione apre un popup in cui compilare dei campi per aggiungere un prodotto
    private fun showDialog(add: String, restName: String?) {
        val inflater = LayoutInflater.from(context)
        val v = inflater.inflate(R.layout.fragment_r_add_to_menu, null)
        val addDialog = AlertDialog.Builder(context)
        val nomeP = v.findViewById<EditText>(R.id.nome_prodotto)
        val prezzoP = v.findViewById<EditText>(R.id.prezzo_prodotto)
        prezzoP.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
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
                UUID.randomUUID().toString().replace("-", "").take(10)
            )
            when (add) {
                "bevanda" -> addProdotto(restName, pData, "Bevande", context)
                "antipasto" -> addProdotto(restName, pData, "Antipasti", context)
                "primo" -> addProdotto(restName, pData, "Primi", context)
                "secondo" -> addProdotto(restName, pData, "Secondi", context)
                "contorno" -> addProdotto(restName, pData, "Contorni", context)
                "dolce" -> addProdotto(restName, pData, "Dolci", context)
            }
        }
        addDialog.setNegativeButton("Indietro") { dialog, _ ->
            dialog.cancel()
        }
        addDialog.create()
        addDialog.show()
    }
}