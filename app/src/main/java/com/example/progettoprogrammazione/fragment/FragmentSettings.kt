package com.example.progettoprogrammazione.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.activity.IntroActivity
import com.example.progettoprogrammazione.databinding.ProfileSettingsBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackUser
import com.example.progettoprogrammazione.models.User
import com.example.progettoprogrammazione.utils.ResponseUser
import com.example.progettoprogrammazione.utils.UserUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FragmentSettings : Fragment(), UserUtils {

    private lateinit var binding: ProfileSettingsBinding

    private lateinit var user: User


    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileSettingsBinding.inflate(layoutInflater)

        val args = this.arguments
        user = args?.getParcelable<User>("user") as User

        when (user.Livello) {
            "1" -> binding.constraintmembrostaff.isVisible = true
            else -> binding.constraintmembrostaff.isVisible = false
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.membrostaff.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("user", user)
            view.findNavController().navigate(R.id.SettingsToUpdate, bundle)
        }

        binding.constraintsalva.setOnClickListener {

            val newnome = binding.nomesettings.text.toString()
            val newcognome = binding.cognomesettings.text.toString()
            val newpassword = binding.passwordsettings.text.toString()
            val newrpassword = binding.ripetipasswordsettings.text.toString()

            if (newnome.length > 20 && newnome.isNotEmpty()) {
                binding.nomesettings.error = "Il nome non può essere lungo più di 20 caratteri."
            }
            if (newcognome.length > 20 && newcognome.isNotEmpty()) {
                binding.cognomesettings.error =
                    "Il cognome non può essere lungo più di 20 caratteri."
            }
            if (newpassword.length < 6 && newpassword.isNotEmpty()) {
                binding.passwordsettings.error = "La password deve essere lunga almeno 6 caratteri."
            }

            if (newrpassword != newpassword) {
                binding.ripetipasswordsettings.error = "Le password non corrispondono."
            }

            val childUpdates: HashMap<String, Any> = hashMapOf()
            if (newnome.isNotEmpty() && newnome.length < 21) childUpdates["Nome"] = newnome
            if (newcognome.isNotEmpty() && newcognome.length < 21) childUpdates["Cognome"] =
                newcognome

            if (childUpdates.isNotEmpty()) {

                if (newrpassword.isNotEmpty() && newrpassword.length > 5) {
                    updateUserPassword(context, newrpassword, user.Email.toString())
                    childUpdates["Password"] = newrpassword
                }
                updateUserData(context, childUpdates)
                getUserData(object : FireBaseCallbackUser {
                    override fun onResponse(responseU: ResponseUser) {
                        val bundleU = Bundle()
                        bundleU.putParcelable("user", responseU.user)
                        when (user.Livello) {
                            "1" -> view.findNavController()
                                .navigate(R.id.SettingsToProfileU, bundleU)
                            "2" -> view.findNavController()
                                .navigate(R.id.SettingsToProfileD, bundleU)
                            "3" -> view.findNavController()
                                .navigate(R.id.SettingsToProfileR, bundleU)
                        }
                    }
                }, context)
            } else {
                Toast.makeText(context, "Applica delle modifiche per poter continuare.", Toast.LENGTH_SHORT).show()
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