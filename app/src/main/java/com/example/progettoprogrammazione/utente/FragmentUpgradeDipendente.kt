package com.example.progettoprogrammazione.utente

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.progettoprogrammazione.activity.EmployeeActivity
import com.example.progettoprogrammazione.databinding.FragmentUpgrDipendenteBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackUser
import com.example.progettoprogrammazione.models.Dipendente
import com.example.progettoprogrammazione.models.User
import com.example.progettoprogrammazione.utils.DipendenteUtils
import com.example.progettoprogrammazione.utils.ResponseUser
import com.example.progettoprogrammazione.utils.UserUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class FragmentUpgradeDipendente : Fragment(), UserUtils, DipendenteUtils {

    private lateinit var binding: FragmentUpgrDipendenteBinding

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpgrDipendenteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = this.arguments
        user = args?.getParcelable<User>("user") as User

        binding.dataAssunzioneD.setOnDateChangeListener { _, year, month, dayOfMonth ->
            binding.dataAssunzioneDT.text = "$dayOfMonth/${month+1}/$year"
        }

        binding.ConstraintUpgradeDip.setOnClickListener {

            val codiceR = binding.codiceRistD.text.toString()
            val partTimeD = binding.partTimeD
            val dataAssunzione = binding.dataAssunzioneDT.text.toString()

            val partime: Boolean = partTimeD.isChecked

            if (codiceR.isNotEmpty() &&  dataAssunzione.isNotEmpty()) {

                getUserData(object : FireBaseCallbackUser {
                    override fun onResponse(responseU: ResponseUser) {
                        val childUpdates = hashMapOf<String, Any>(
                            "Livello" to "2"
                        )
                        updateUserData(
                            context, childUpdates
                        )
                        val dipendenteData = Dipendente(
                            user.Nome,
                            user.Cognome,
                            user.Telefono,
                            "--:-- | --:--",
                            dataAssunzione,
                            "2",
                            partime,
                            "€€€",
                            UUID.randomUUID().toString().replace("-","").take(10),
                            user.Email,
                            codiceR
                        )
                        createDipendente(context, dipendenteData)
                        val intent =
                            Intent(context, EmployeeActivity::class.java).apply {
                                putExtra("user", responseU.user)
                            }
                        startActivity(intent)
                        activity?.finish()
                    }
                }, context)
            } else {
                Toast.makeText(context, "Nessun campo può essere vuoto!", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }
}