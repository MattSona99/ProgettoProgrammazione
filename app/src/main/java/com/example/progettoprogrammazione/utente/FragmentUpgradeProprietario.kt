package com.example.progettoprogrammazione.utente

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.progettoprogrammazione.activity.RestaurateurActivity
import com.example.progettoprogrammazione.activity.UserActivity
import com.example.progettoprogrammazione.databinding.FragmentUpgrProprietarioBinding
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.utils.*
import com.example.progettoprogrammazione.viewmodels.RestaurantViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.ArrayList

class FragmentUpgradeProprietario : Fragment(), UserUtil, RestaurantUtils {

    private lateinit var binding: FragmentUpgrProprietarioBinding

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    private lateinit var resturantDataViewModel: RestaurantViewModel
    private lateinit var restaurantData: Restaurant
    private lateinit var restArrayList: ArrayList<Restaurant>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpgrProprietarioBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.ConstraintCreaRist.setOnClickListener {

            val ImgR = binding.nomeristoranteNewR.text.toString()
            val NomeR = binding.nomeristoranteNewR.text.toString()
            val DescrizioneR = binding.descrizioneNewR.text.toString()
            val IndirizzoR = binding.indirizzoNewR.text.toString()
            val orariolavorativoR = binding.orariolavorativoNewR.text.toString()
            val telefonoR = binding.telefonoNewR.text.toString()
            val tipoCiboR = binding.tipociboNewR.text.toString()
            //DA CAMBIARE NON SONO DI TIPO STRINGA
            val veganR = binding.veganNewR
            var vegan: Boolean
/*
            if (NomeR.length != null && NomeR.length < 25) {
                binding.nomeristoranteNewR.setError("Il nome non può essere lungo più di 20 caratteri.")
            }
            if (DescrizioneR.length != null && DescrizioneR.length < 250) {
                binding.nomeristoranteNewR.setError("Descrizione vuota o troppo lunga")
            }
            if (IndirizzoR.length != null && IndirizzoR.length > 15) {
                binding.indirizzoNewR.setError("Indririzzo Errrato o vuoto.")
            }
            if (orariolavorativoR.length!= null ) {
                binding.nomeristoranteNewR.setError("Formato Errato quello corretto e'(gg: xx:xx-xx:xx)  ")
            }
            if (telefonoR != null && telefonoR.length > 9) {
                binding.nomeristoranteNewR.setError("Numero telefono deve contenere almeno 9 caratteri")
            }
            if (tipoCiboR.length != null ) {
                binding.nomeristoranteNewR.setError("Inserisci una tipologia di cibo")
            }
            */
            vegan = veganR.isChecked

            resturantDataViewModel =
                ViewModelProvider(requireActivity())[RestaurantViewModel::class.java]
            resturantDataViewModel.arrayListaRistorantiLiveData.observe(viewLifecycleOwner) { arraylist ->
                restArrayList = arraylist

                restaurantData = Restaurant(
                    "Restaurants-images/defaultrestaurantimg",
                    NomeR,
                    DescrizioneR,
                    IndirizzoR,
                    orariolavorativoR,
                    telefonoR,
                    tipoCiboR,
                    vegan,
                    "1.0",
                    UUID.randomUUID().toString()
                )
                val childUpdates = hashMapOf<String, Any>(
                    "Livello" to "1"
                )
                updateUserData(
                    context, childUpdates
                )
                getUserData(object : FireBaseCallbackUser {
                    override fun onResponse(responseU: ResponseUser) {
                        createRestaurant(context, restaurantData)
                        getRestaurantData(object : FireBaseCallbackRestaurant {
                            override fun onResponse(responseR: ResponseRistorante) {
                                restArrayList = responseR.ristoranti
                                val intent =
                                    Intent(context, UserActivity::class.java).apply {
                                        putExtra("user", responseU.user)
                                        putParcelableArrayListExtra(
                                            "ristoranti",
                                            responseR.ristoranti
                                        )
                                    }
                                startActivity(intent)
                                activity?.finish()
                            }
                        }, context)


                    }
                }, context)
            }
        }
    }
}
