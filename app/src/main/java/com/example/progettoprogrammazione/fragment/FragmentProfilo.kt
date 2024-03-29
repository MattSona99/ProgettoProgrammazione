package com.example.progettoprogrammazione.fragment

import android.app.AlertDialog
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.Fragment1ProfiloBinding
import com.example.progettoprogrammazione.models.User
import com.example.progettoprogrammazione.firebase.FireBaseCallbackUser
import com.example.progettoprogrammazione.utils.ImgUtils
import com.example.progettoprogrammazione.utils.ResponseUser
import com.example.progettoprogrammazione.utils.UserUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import kotlin.collections.HashMap


// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Questo fragment permette all'utente di mostrare le proprie informazioni principali e navigare nelle impostazioni

class FragmentProfilo : Fragment(), UserUtils, ImgUtils {

    private lateinit var binding: Fragment1ProfiloBinding

    private lateinit var user: User

    private lateinit var imageUri: Uri
    private var newimg: String? = null

    // Funzione che permette di selezionare un'immagine dalla galleria ed effettuare l'update sul database
    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri = uri
                if (this::imageUri.isInitialized) {
                    val builder = AlertDialog.Builder(activity)
                    builder.setMessage("Sei sicuro di voler caricare questa immagine?")
                    builder.setPositiveButton("Sì") { dialog, _ ->
                        if (imageUri != null) {
                            fileName = uploadImage(imageUri, "Users")
                            newimg = "Users-images/" + fileName
                            val childUpdates: HashMap<String, Any> = hashMapOf()
                            childUpdates["Uri"] = newimg!!
                            updateUserData(context, childUpdates)
                            getUserData(object : FireBaseCallbackUser {
                                override fun onResponse(responseU: ResponseUser) {
                                    val bundleU = Bundle()
                                    bundleU.putParcelable("user", responseU.user)
                                    when (user.Livello) {
                                        "1" -> view!!.findNavController()
                                            .navigate(R.id.ProfiloUSelf, bundleU)
                                        "2" -> view!!.findNavController()
                                            .navigate(R.id.ProfiloDSelf, bundleU)
                                        "3" -> view!!.findNavController()
                                            .navigate(R.id.ProfiloRSelf, bundleU)
                                    }
                                }
                            }, context)
                        }

                    }
                    builder.setNegativeButton("No") { dialog, _ ->
                        dialog.cancel()
                    }
                    builder.show()
                }
            }
        }
    lateinit var fileName: String

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Fragment1ProfiloBinding.inflate(layoutInflater)

        // Richiama i dati dell'utente loggato e li posiziona all'interno del fragment
        getUserData(object : FireBaseCallbackUser {
            override fun onResponse(responseU: ResponseUser) {
                user = responseU.user!!
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
                binding.nomeprofilo.hint = user.Nome
                binding.cognomeprofilo.hint = user.Cognome
                binding.telefonoprofilo.hint = user.Telefono
                binding.emailprofilo.hint = user.Email
            }

        }, context)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Permette, cliccando sull'immagine di profilo, di richiamare la funzione per modificarla
        binding.imgProfiloUtente.setOnClickListener {
            selectImageFromGallery()
        }

        // Permette di navigare alle impostazioni dell'utente
        binding.settings.setOnClickListener {
            getUserData(object : FireBaseCallbackUser {
                override fun onResponse(responseU: ResponseUser) {
                    val bundleU = Bundle()
                    bundleU.putParcelable("user", responseU.user)
                    when (user.Livello) {
                        "1" -> view.findNavController()
                            .navigate(R.id.Profilo_U_to_Settings, bundleU)
                        "2" -> view.findNavController()
                            .navigate(R.id.Profilo_D_to_Settings, bundleU)
                        "3" -> view.findNavController()
                            .navigate(R.id.Profilo_R_to_Settings, bundleU)
                    }
                }
            }, context)
        }

    }

    override fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")

    private fun getImageId(context: Context, imageName: String): Int {
        return context.resources
            .getIdentifier("drawable/$imageName", null, context.packageName)
    }
}

