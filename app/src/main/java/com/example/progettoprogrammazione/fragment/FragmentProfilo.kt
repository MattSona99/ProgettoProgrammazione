package com.example.progettoprogrammazione.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.FragmentProfiloBinding
import com.example.progettoprogrammazione.models.User
import com.example.progettoprogrammazione.utils.UserUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class FragmentProfilo : Fragment(), UserUtil {

    private lateinit var binding: FragmentProfiloBinding

    private lateinit var user: User

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfiloBinding.inflate(layoutInflater)

        val args = this.arguments
        user = args?.getParcelable<User>("user") as User

        binding.nicknameprofilo.text = user.Nome + " " + user.Cognome
        binding.nomeprofilo.hint = "Nome: " + user.Nome
        binding.cognomeprofilo.hint = "Cognome: " + user.Cognome
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.creaRistorante.setOnClickListener {
            view.findNavController().navigate(R.id.ProfiloToUpgrade)
        }

        binding.salva.setOnClickListener {

            val newnome = binding.nomeprofilo.text.toString()
            val newcognome = binding.cognomeprofilo.text.toString()
            val newpassword = binding.passwordprofilo.text.toString()
            val newrpassword = binding.rpasswordprofilo.text.toString()

            if (newnome.length > 20) {
                binding.nomeprofilo.error = "Il nome non può essere lungo più di 20 caratteri."
            }
            if (newcognome.length > 20) {
                binding.cognomeprofilo.error = "Il cognome non può essere lungo più di 20 caratteri."
            }
            if (newpassword.length < 6) {
                binding.passwordprofilo.error = "La password deve essere lunga almeno 6 caratteri."
            }

            if (newrpassword != newpassword) {
                binding.rpasswordprofilo.error = "Le password non corrispondono."
            }
            if (newrpassword.isNotEmpty() && newpassword.isNotEmpty()) {
                updateUserPassword(context, newrpassword, user.Email.toString())
                val childUpdates: HashMap<String, Any> = hashMapOf()
                if (newnome.isNotEmpty()) childUpdates["Nome"] = newnome
                if (newcognome.isNotEmpty()) childUpdates["Cognome"] = newcognome
                if (newrpassword.isNotEmpty()) childUpdates["Password"] = newrpassword
                updateUserData(context, childUpdates)

            }
        }

        super.onViewCreated(view, savedInstanceState)
    }
}

