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
import com.example.progettoprogrammazione.databinding.Fragment0RegistratiBinding
import com.example.progettoprogrammazione.models.User
import com.example.progettoprogrammazione.firebase.FireBaseCallbackUser
import com.example.progettoprogrammazione.utils.UserUtils
import com.example.progettoprogrammazione.utils.ResponseUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
/* Chi utilizza questo fragment può effettuare la registrazione compilando appositamente
  tutti i campi richiesti, oppure può navigare alla pagina Login;
  Alla fine della registrazione, si navigherà alla pagina Login e verrà chiesto l'accesso */


class FragmentRegister : Fragment(), UserUtils {

    private lateinit var binding: Fragment0RegistratiBinding
    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Fragment0RegistratiBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Quando tutti i campi sono compilati correttamente, verrà creata un'istanza
        // authentication e verranno salvati i dati dell'utente sul database
        binding.ConstraintRegistrati.setOnClickListener {

            val nome = binding.nome.text.toString()
            val cognome = binding.cognome.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val telefono = binding.telefono.text.toString()

            if (nome.length > 20) {
                binding.nome.error = "Il nome non può essere lungo più di 20 caratteri."
            }
            if (cognome.length > 20) {
                binding.cognome.error = "Il cognome non può essere lungo più di 20 caratteri."
            }
            if (email.length > 40) {
                binding.email.error = "L'email non può essere lunga più di 40 caratteri."
            }
            if (TextUtils.isEmpty(email)) {
                binding.email.error = "Inserisci un'email valida."
            }
            if (password.length < 6) {
                binding.password.error = "La password deve essere lunga almeno 6 caratteri."
            }
            if (telefono.length > 11) {
                binding.telefono.error = "Il numero di telefono deve contenere al massimo 11 cifre."
            }

            if (nome.isNotEmpty() && nome.length < 20
                && cognome.isNotEmpty() && cognome.length < 20
                && email.isNotEmpty() && email.length < 41
                && password.isNotEmpty() && password.length > 5
                && telefono.isNotEmpty() && telefono.length > 9) {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val save = User(
                                nome,
                                cognome,
                                email,
                                password,
                                telefono,
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
                                            override fun onResponse(responseU: ResponseUser) {
                                                val intent =
                                                    Intent(context, IntroActivity::class.java)
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

            } else {
                Toast.makeText(context, "Nessun campo può essere vuoto!", Toast.LENGTH_LONG).show()
            }
        }

        // Cliccando sul bottone, la navigazione porterà alla pagina "login"
        binding.already.setOnClickListener {
            view.findNavController().navigate(R.id.RegisterToLogin)
        }
    }
}