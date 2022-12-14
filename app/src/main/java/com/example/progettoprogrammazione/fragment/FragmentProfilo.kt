package com.example.progettoprogrammazione.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.FragmentProfiloBinding
import com.example.progettoprogrammazione.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FragmentProfilo : Fragment() {

    private lateinit var binding: FragmentProfiloBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentProfiloBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.creaRistorante.setOnClickListener() {
            view.findNavController().navigate(R.id.ProfiloToUpgrade)
        }

        super.onViewCreated(view, savedInstanceState)
        val benvenutou = view.findViewById<TextView>(R.id.benvenutoprofilo)
        val nomeu = view.findViewById<EditText>(R.id.nomeprofilo)
        val cognomeu = view.findViewById<EditText>(R.id.cognomeprofilo)
        val passwordu = view.findViewById<EditText>(R.id.passwordprofilo)
        getUserData(object : FireBaseCallback {
            override fun onResponse(response: Response) {
                benvenutou.text =
                    "Benvenuto, " + response.user!!.Nome + ".\nQui potrai modificare i tuoi dati personali!"
                nomeu.hint = "Nome: " + response.user!!.Nome
                cognomeu.hint = "Cognome: " + response.user!!.Cognome
                passwordu.hint = "Password: " + response.user!!.Password
            }
        })
    }

    private interface FireBaseCallback {
        fun onResponse(response: Response)
    }

    data class Response(
        var user: User? = null
    )


    private fun getUserData(callBack: FireBaseCallback) {
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()

        firebaseDatabase.getReference("Utenti").child(firebaseAuth.currentUser!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val response = Response()
                    response.user = User(
                        snapshot.child("Nome").value.toString(),
                        snapshot.child("Cognome").value.toString(),
                        snapshot.child("Email").value.toString(),
                        snapshot.child("Password").value.toString(),
                        snapshot.child("Telefono").value.toString(),
                        snapshot.child("Uri").value.toString(),
                        snapshot.child("Livello").value.toString()
                    )
                    callBack.onResponse(response)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        requireActivity(),
                        "Errore durante il caricamento dei dati",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }


}

