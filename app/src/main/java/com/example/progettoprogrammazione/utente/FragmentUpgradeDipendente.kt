package com.example.progettoprogrammazione.utente

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.progettoprogrammazione.activity.EmployeeActivity
import com.example.progettoprogrammazione.activity.RestaurateurActivity
import com.example.progettoprogrammazione.databinding.FragmentUpgrDipendenteBinding
import com.example.progettoprogrammazione.models.Dipendente
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.utils.DipendenteUtil
import com.example.progettoprogrammazione.utils.FireBaseCallbackUser
import com.example.progettoprogrammazione.utils.ResponseUser
import com.example.progettoprogrammazione.utils.UserUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FragmentUpgradeDipendente : Fragment() , UserUtil,DipendenteUtil{

    private lateinit var binding : FragmentUpgrDipendenteBinding

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

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

            val childUpdates = hashMapOf<String, Any>(
                "Livello" to "2"
            )
            updateUserData(
                context, childUpdates
            )
            getUserData(object : FireBaseCallbackUser {
                override fun onResponse(response: ResponseUser) {
                    createDipendente(context, dipendenteData)
                    val intent= Intent(context, EmployeeActivity::class.java).apply {
                        putExtra("user", response.user)
                    }
                    startActivity(intent)
                    activity?.finish()
                }
            }, context)


        }

    }

}