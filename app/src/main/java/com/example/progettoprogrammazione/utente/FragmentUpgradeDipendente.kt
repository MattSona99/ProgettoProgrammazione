package com.example.progettoprogrammazione.utente

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.progettoprogrammazione.activity.RestaurateurActivity
import com.example.progettoprogrammazione.databinding.FragmentUpgrDipendenteBinding
import com.example.progettoprogrammazione.models.Dipendente
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.utils.*
import com.example.progettoprogrammazione.viewmodels.RestaurantViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class FragmentUpgradeDipendente : Fragment(), UserUtil, DipendenteUtil {

    private lateinit var binding: FragmentUpgrDipendenteBinding

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    private lateinit var resturantDataViewModel: RestaurantViewModel
    private lateinit var restArrayList: ArrayList<Restaurant>

    private lateinit var dipendenteData: Dipendente

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpgrDipendenteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ConstraintUpgradeDip.setOnClickListener {

            binding.nomedipNewD.text.toString()
            val nomeD = binding.nomedipNewD.text.toString()
            val cognomeD = binding.cognomeNewD.text.toString()
            val businessEmailD = binding.bemailNewD.text.toString()
            val telefonoD = binding.telefonodNewD.text.toString()
            val partTimeD = binding.parttimeDip

            if (nomeD.length > 20) {
                binding.nomedipNewD.error =
                    "Il nome non può essere lungo più di 20 caratteri."
            }
            if (cognomeD.length > 25) {
                binding.cognomeNewD.error = "Il cognome non può essere lungo più di 25 caratteri"
            }
            if (TextUtils.isEmpty(businessEmailD) && businessEmailD.length > 35) {
                binding.bemailNewD.error = "Inserisci un'email valida."
            }
            if (telefonoD.length < 9)
                binding.telefonodNewD.error =
                    "Il numero di telefono deve contenere almeno 9 caratteri"

            val partime: Boolean = partTimeD.isChecked

            if (nomeD.isNotEmpty() && cognomeD.isNotEmpty() &&
                businessEmailD.isNotEmpty() && telefonoD.isNotEmpty() ) {

                getUserData(object : FireBaseCallbackUser {
                    override fun onResponse(responseU: ResponseUser) {
                        val childUpdates = hashMapOf<String, Any>(
                            "Livello" to "2"
                        )
                        updateUserData(
                            context, childUpdates
                        )
                        //PRENDO DATI RISTORANTE
                        resturantDataViewModel= ViewModelProvider(requireActivity())[RestaurantViewModel::class.java]
                        resturantDataViewModel.arrayListRistorantiLiveData.observe(viewLifecycleOwner) {
                        restArrayList=it
                        }

                        dipendenteData = Dipendente(
                            nomeD,
                            cognomeD,
                            businessEmailD,
                            telefonoD,
                            "Giornaliero",
                            "xx/xx/xxxx",
                            "xx/xx/xxxx",
                            "Dipendente-images/defaultrestaurantimg",
                            "2",
                            partime,
                            1000,
                            UUID.randomUUID().toString(),
                            "non assunto"
                        )
                        createDipendente(context, dipendenteData)
                        getDipendenteData(object : FireBaseCallbackDipendente {
                                override fun onResponse(responseD: ResponseDipendente) {
                                    val intent =
                                        Intent(context, RestaurateurActivity::class.java).apply {
                                            putExtra("user", responseU.user)
                                            putParcelableArrayListExtra(
                                                "dipendenti",
                                                responseD.dipendenti
                                            )
                                        }
                                    startActivity(intent)
                                    activity?.finish()
                                }
                            }, context
                        )
                    }
                }, context)
            } else {
                Toast.makeText(context, "Nessun campo può essere vuoto!", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }
}