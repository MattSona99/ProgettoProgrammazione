package com.example.progettoprogrammazione.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.progettoprogrammazione.databinding.ActivityLoginBinding
import com.example.progettoprogrammazione.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var userlvl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.ConstraintEntra.setOnClickListener() {
            val Email = binding.email.text.toString()
            val Password = binding.password.text.toString()


            //IF SCRITTO IN MANIERA MIGLIORE
            if (Email.isNotEmpty() && Password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener {
                    if (it.isSuccessful) {

                        getUserData(object : FireBaseCallback {
                            override fun onResponse(response: Response) {
                                userlvl = response.user!!.Livello

                                Toast.makeText(
                                    this@LoginActivity,
                                    "Login effettuato con successo!",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                                if (userlvl.equals("1")) {
                                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    startActivity(intent)
                                } else if (userlvl.equals("2")) {
                                    val intent = Intent(this@LoginActivity, EmployeeActivity::class.java)
                                    startActivity(intent)
                                } else if (userlvl .equals("3") ) {
                                    val intent = Intent(this@LoginActivity, RestaurateurActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Email e password non corrispondono!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        })
                    }
                }
            } else {
                Toast.makeText(this, "Nessun campo può essere vuoto!", Toast.LENGTH_LONG).show()
            }
        }

        binding.noaccount.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()

        if (firebaseAuth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    //PRENDIAMO I DATI DEGLI USER
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
                        this@LoginActivity,
                        "Errore durante il caricamento dei dati",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}
