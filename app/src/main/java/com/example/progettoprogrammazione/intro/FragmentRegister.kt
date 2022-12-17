package com.example.progettoprogrammazione.intro

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.activity.IntroActivity
import com.example.progettoprogrammazione.databinding.FragmentRegistratiBinding
import com.example.progettoprogrammazione.models.User
import com.example.progettoprogrammazione.utils.FireBaseCallbackUser
import com.example.progettoprogrammazione.utils.UserUtil
import com.example.progettoprogrammazione.utils.ResponseUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class FragmentRegister : Fragment(), UserUtil {

    private lateinit var binding: FragmentRegistratiBinding
    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistratiBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ConstraintRegistrati.setOnClickListener {

            val Nome = binding.nome.text.toString()
            val Cognome = binding.cognome.text.toString()
            val Email = binding.email.text.toString()
            val Password = binding.password.text.toString()
            val Telefono = binding.telefono.text.toString()

            if (Nome.length > 20) {
                binding.nome.error = "Il nome non può essere lungo più di 20 caratteri."
            }
            if (Cognome.length > 20) {
                binding.cognome.error = "Il cognome non può essere lungo più di 20 caratteri."
            }
            if (Email.length > 40) {
                binding.email.error = "L'email non può essere lunga più di 40 caratteri."
            }
            if (TextUtils.isEmpty(Email)) {
                binding.email.error = "Inserisci un'email valida."
            }
            if (Password.length < 6) {
                binding.password.error = "La password deve essere lunga almeno 6 caratteri."
            }
            if (Telefono.length > 11) {
                binding.telefono.error = "Il numero di telefono deve contenere al massimo 11 cifre."
            }

            if (Nome.isNotEmpty() && Cognome.isNotEmpty() && Email.isNotEmpty() && Password.isNotEmpty() && Telefono.isNotEmpty()) {
                firebaseAuth.createUserWithEmailAndPassword(Email, Password)
                    .addOnCompleteListener{
                        if (it.isSuccessful) {
                            val save = User(
                                Nome,
                                Cognome,
                                Email,
                                Password,
                                Telefono,
                                "Users-images/defaultuserimg",
                                "1"
                            )
                            val user = mutableMapOf<String, String>()
                            user["Nome"] = save.Nome!!
                            user["Cognome"] = save.Cognome!!
                            user["Email"] = save.Email!!
                            user["Password"] = save.Password!!
                            user["Telefono"] = save.Telefono!!
                            user["Uri"] = save.Uri!!
                            user["Livello"] = save.Livello!!

                            firebaseDatabase.getReference("Utenti")
                                .child(firebaseAuth.currentUser!!.uid).setValue(user)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(
                                            context,
                                            "Utente creato con successo.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        getUserData(object : FireBaseCallbackUser {
                                            override fun onResponse(response: ResponseUser) {
                                                val intent = Intent(context, IntroActivity::class.java)
                                                firebaseAuth.signOut()
                                                startActivity(intent)
                                                activity?.finish()
                                            }
                                        }, context)
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Errore durante la registrazione. Riprova!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                        } else {
                            Toast.makeText(
                                context,
                                "Errore durante la registrazione.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

            }
        }
        binding.already.setOnClickListener() {
            view.findNavController().navigate(R.id.RegisterToLogin)
        }
    }
}