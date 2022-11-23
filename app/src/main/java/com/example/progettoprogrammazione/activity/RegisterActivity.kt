package com.example.progettoprogrammazione.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.progettoprogrammazione.databinding.ActivityRegistratiBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistratiBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistratiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()

        binding.ConstraintRegistrati.setOnClickListener {

            val Nome = binding.nome.text.toString()
            val Cognome = binding.cognome.text.toString()
            val Email = binding.email.text.toString()
            val Password = binding.password.text.toString()
            val Telefono = binding.telefono.text.toString()

            if (Nome.length > 20) {
                binding.nome.setError("Il nome non può essere lungo più di 20 caratteri.")
            }
            if (Cognome.length > 20) {
                binding.cognome.setError("Il cognome non può essere lungo più di 20 caratteri.")
            }
            if (Email.length > 40) {
                binding.email.setError("L'email non può essere lunga più di 40 caratteri.")
            }
            if(TextUtils.isEmpty(Email)){
                binding.email.setError("Inserisci un'email valida.")
            }
            if (Password.length < 6) {
                binding.password.setError("La password deve essere lunga almeno 6 caratteri.")
            }
            if (Telefono.length > 11) {
                binding.telefono.setError("Il numero di telefono deve contenere al massimo 11 cifre.")
            }

            if (Nome.isNotEmpty() && Cognome.isNotEmpty() && Email.isNotEmpty() && Password.isNotEmpty() && Telefono.isNotEmpty()) {
                firebaseAuth.createUserWithEmailAndPassword(Email, Password)
                    .addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            val User = mutableMapOf<String, String>()
                            User.put("Nome", Nome)
                            User.put("Cognome", Cognome)
                            User.put("Email", Email)
                            User.put("Password", Password)
                            User.put("Telefono", Telefono)
                            User.put("Uri", "Users-images/defaultuserimg")
                            User.put("Livello", "1")


                            firebaseDatabase.getReference("Utenti")
                                .child(firebaseAuth.currentUser!!.uid).setValue(User)
                                .addOnCompleteListener {task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(
                                            this,
                                            "Utente creato con successo.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        val intent = Intent(this, MainActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(
                                            this,
                                            "Errore durante la registrazione. Riprova!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                        } else {
                            Toast.makeText(
                                this,
                                "Errore durante la registrazione.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

            }

            binding.already.setOnClickListener() {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}