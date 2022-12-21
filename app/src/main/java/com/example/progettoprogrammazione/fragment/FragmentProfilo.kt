package com.example.progettoprogrammazione.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.activity.IntroActivity
import com.example.progettoprogrammazione.databinding.FragmentProfiloBinding
import com.example.progettoprogrammazione.models.User
import com.example.progettoprogrammazione.utils.FireBaseCallbackUser
import com.example.progettoprogrammazione.utils.ResponseUser
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

        when(user.Livello){
            "1" -> binding.constraintmembrostaff.isVisible = true
            else -> binding.constraintmembrostaff.isVisible = false
        }

        binding.nicknameprofilo.text = user.Nome + " " + user.Cognome
        binding.nomeprofilo.hint = "Nome: " + user.Nome
        binding.cognomeprofilo.hint = "Cognome: " + user.Cognome
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.membrostaff.setOnClickListener {
            view.findNavController().navigate(R.id.ProfiloToUpgrade)
        }

        binding.salva.setOnClickListener {

            val newnome = binding.nomeprofilo.text.toString()
            val newcognome = binding.cognomeprofilo.text.toString()
            val newpassword = binding.passwordprofilo.text.toString()
            val newrpassword = binding.rpasswordprofilo.text.toString()

            if (newnome.length > 20 && newnome.isNotEmpty()) {
                binding.nomeprofilo.error = "Il nome non può essere lungo più di 20 caratteri."
            }
            if (newcognome.length > 20 && newcognome.isNotEmpty()) {
                binding.cognomeprofilo.error =
                    "Il cognome non può essere lungo più di 20 caratteri."
            }
            if (newpassword.length < 6 && newpassword.isNotEmpty()) {
                binding.passwordprofilo.error = "La password deve essere lunga almeno 6 caratteri."
            }

            if (newrpassword != newpassword) {
                binding.rpasswordprofilo.error = "Le password non corrispondono."
            }

            val childUpdates: HashMap<String, Any> = hashMapOf()
            if (newnome.isNotEmpty()) childUpdates["Nome"] = newnome
            if (newcognome.isNotEmpty()) childUpdates["Cognome"] = newcognome

            if (newrpassword == newpassword || (newrpassword.isEmpty() && newpassword.isEmpty())) {
                if(newrpassword.isNotEmpty()){
                updateUserPassword(context, newrpassword, user.Email.toString())
                childUpdates["Password"] = newrpassword
                }
                updateUserData(context, childUpdates)
                getUserData(object : FireBaseCallbackUser {
                    override fun onResponse(responseU: ResponseUser) {
                        val bundleU = Bundle()
                        bundleU.putParcelable("user", responseU.user)
                        when(user.Livello){
                            "1" -> view.findNavController().navigate(R.id.ProfiloUSelf, bundleU)
                            //"2" -> view.findNavController().navigate(R.id.ProfiloDSelf, bundleU)
                            "3" -> view.findNavController().navigate(R.id.ProfiloRSelf, bundleU)
                        }

                    }
                }, context)
            }


        }

        binding.eliminaAccount.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Conferma l'eliminazione dell'account")
            builder.setMessage("Sei sicuro di voler eliminare l'account?")
            builder.setPositiveButton("Sì") { dialog, _ ->
                deleteUserData(context)
                firebaseAuth.signOut()
                val intent = Intent(requireActivity(), IntroActivity::class.java)
                startActivity(intent)
                activity?.finish()
                dialog.cancel()
            }
            builder.setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
            }
            builder.show()
        }
    }
}

