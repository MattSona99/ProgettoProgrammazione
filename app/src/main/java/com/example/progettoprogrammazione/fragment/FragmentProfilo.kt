package com.example.progettoprogrammazione.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.activity.IntroActivity
import com.example.progettoprogrammazione.databinding.FragmentProfiloBinding
import com.example.progettoprogrammazione.models.User
import com.example.progettoprogrammazione.firebase.FireBaseCallbackUser
import com.example.progettoprogrammazione.utils.ImgUtils
import com.example.progettoprogrammazione.utils.ResponseUser
import com.example.progettoprogrammazione.utils.UserUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.util.*
import kotlin.collections.HashMap


class FragmentProfilo : Fragment(), UserUtils, ImgUtils {

    private lateinit var binding: FragmentProfiloBinding

    private lateinit var user: User

    private lateinit var imageUri: Uri
    private var newimg: String? = null

    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri = uri
            }
        }
    lateinit var fileName: String

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfiloBinding.inflate(layoutInflater)

        val args = this.arguments
        user = args?.getParcelable<User>("user") as User

        when (user.Livello) {
            "1" -> binding.constraintmembrostaff.isVisible = true
            else -> binding.constraintmembrostaff.isVisible = false
        }

        val imageName = user.Uri
        val storageRef = FirebaseStorage.getInstance().reference.child("$imageName")
        val localfile = File.createTempFile("tempImage", "jpg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding.imgProfiloUtente.setImageBitmap(bitmap)
        }
        binding.imgProfiloUtente.setImageResource(
            getImageId(
                binding.root.context,
                user.Uri!!
            )
        )

        binding.nicknameprofilo.text = user.Nome + " " + user.Cognome
        binding.nomeprofilo.hint = "Nome: " + user.Nome
        binding.cognomeprofilo.hint = "Cognome: " + user.Cognome
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.membrostaff.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("user", user)
            view.findNavController().navigate(R.id.ProfiloToUpgrade, bundle)
        }

        binding.imgProfiloUtente.setOnClickListener {
            selectImageFromGallery()
            if (selectImageFromGalleryResult != null && imageUri != null) {
                val builder = AlertDialog.Builder(activity)
                builder.setMessage("Sei sicuro di voler caricare questa immagine?")
                builder.setPositiveButton("Sì") { dialog, _ ->
                    if (imageUri != null) {
                        uploadImage()
                        newimg = "Users-images/" + fileName
                        val childUpdates: HashMap<String, Any> = hashMapOf()
                        childUpdates["Uri"] = newimg!!
                        updateUserData(context, childUpdates)
                        getUserData(object : FireBaseCallbackUser {
                            override fun onResponse(responseU: ResponseUser) {
                                val bundleU = Bundle()
                                bundleU.putParcelable("user", responseU.user)
                                when (user.Livello) {
                                    "1" -> view.findNavController()
                                        .navigate(R.id.ProfiloUSelf, bundleU)
                                    "2" -> view.findNavController()
                                        .navigate(R.id.ProfiloDSelf, bundleU)
                                    "3" -> view.findNavController()
                                        .navigate(R.id.ProfiloRSelf, bundleU)
                                }

                            }
                        }, context)
                    }


                }
                builder.setNegativeButton("No") { dialog, _ ->
                    dialog.cancel()
                }
                Handler().postDelayed({
                    builder.show()
                }, 2000)
            }
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
            if (newnome.isNotEmpty() && newnome.length < 21) childUpdates["Nome"] = newnome
            if (newcognome.isNotEmpty() && newcognome.length < 21) childUpdates["Cognome"] =
                newcognome

            if (newrpassword == newpassword && newrpassword.isEmpty() && newpassword.isEmpty()
                && newpassword.length > 5
            ) {
                if (newrpassword.isNotEmpty()) {
                    updateUserPassword(context, newrpassword, user.Email.toString())
                    childUpdates["Password"] = newrpassword
                }
                updateUserData(context, childUpdates)
                getUserData(object : FireBaseCallbackUser {
                    override fun onResponse(responseU: ResponseUser) {
                        val bundleU = Bundle()
                        bundleU.putParcelable("user", responseU.user)
                        when (user.Livello) {
                            "1" -> view.findNavController().navigate(R.id.ProfiloUSelf, bundleU)
                            "2" -> view.findNavController().navigate(R.id.ProfiloDSelf, bundleU)
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

    override fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")

    override fun uploadImage() {
        fileName = UUID.randomUUID().toString() + ".jpg"

        val refStorage =
            FirebaseStorage.getInstance().getReference("Users-images/").child(fileName)

        refStorage.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                    it.toString()
                }
            }

            .addOnFailureListener { e ->
                print(e.message)
            }
    }

    private fun getImageId(context: Context, imageName: String): Int {
        return context.resources
            .getIdentifier("drawable/$imageName", null, context.packageName)
    }
}

