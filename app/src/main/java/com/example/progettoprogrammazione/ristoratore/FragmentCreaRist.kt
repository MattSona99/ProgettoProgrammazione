package com.example.progettoprogrammazione.ristoratore

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.progettoprogrammazione.activity.CreaMenu
import com.example.progettoprogrammazione.databinding.FragmentCreaRistBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackRestaurant
import com.example.progettoprogrammazione.firebase.FireBaseCallbackUser
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.models.User
import com.example.progettoprogrammazione.utils.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class FragmentCreaRist : Fragment(), UserUtil, RestaurantUtils, ImgUtils {

    private lateinit var binding: FragmentCreaRistBinding

    private lateinit var user: User

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    private lateinit var restaurantData: Restaurant

    private lateinit var imageUri: Uri

    private lateinit var tipocibo: TextView

    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri = uri
            }
        }
    lateinit var fileName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreaRistBinding.inflate(layoutInflater)
        tipocibo = binding.tipociboNewR
        val typearray =
            arrayOf("Italiano", "Cinese", "Giapponese", "Indiano", "Greco", "Pizza", "Burger")
        val typelist = arrayListOf<String>()
        val selected = BooleanArray(typearray.size)

        binding.tipociboNewR.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Seleziona il tipo di cibo")

            val selectedItems = mutableListOf(*typearray)

            builder.setCancelable(false)

            builder.setMultiChoiceItems(typearray, selected) { dialog, which, isChecked ->
                selected[which] = isChecked
                val currentItem = selectedItems[which]
            }

            builder.setPositiveButton("Ok") { dialog, _ ->
                for (i in selected.indices) {
                    if (selected[i]) {
                        binding.tipociboNewR.text =
                            String.format("%s%s, ", binding.tipociboNewR.text, selectedItems[i])
                    }
                }
            }
            builder.setNegativeButton("Annulla") { dialog, _ ->
                dialog.cancel()
            }
            builder.setNeutralButton("Pulisci") { dialog: DialogInterface?, which: Int ->
                Arrays.fill(selected, false)
                binding.tipociboNewR.text = ""
            }
            builder.show()
        }

        val args = this.arguments
        user = args?.getParcelable<User>("user") as User

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.selectImgNewR.setOnClickListener {
            selectImageFromGallery()
        }

        binding.ConstraintCreaRist.setOnClickListener {
            if (this::imageUri.isInitialized) {
                binding.nomeristoranteNewR.text.toString()
                val nomeR = binding.nomeristoranteNewR.text.toString()
                val descrizioneR = binding.descrizioneNewR.text.toString()
                val indirizzoR = binding.indirizzoNewR.text.toString()
                val orariolavorativoR = binding.orariolavorativoNewR.text.toString()
                val telefonoR = binding.telefonoNewR.text.toString()
                val tipoCiboR = binding.tipociboNewR.text.toString()
                val veganR = binding.veganNewR
                uploadImage()

                if (nomeR.length > 35) {
                    binding.nomeristoranteNewR.error =
                        "Il nome non può essere lungo più di 35 caratteri."
                }
                if (descrizioneR.length > 250 || descrizioneR.length < 50) {
                    binding.descrizioneNewR.error =
                        "La descrizione deve essere compresa tra 50 e 250 caratteri"
                }
                if (indirizzoR.length < 15) {
                    binding.indirizzoNewR.error = "Indririzzo errato o vuoto."
                }
                if (orariolavorativoR.length < 11)
                    binding.orariolavorativoNewR.error = "Formato errato. (gg: xx:xx-xx:xx)"

                if (telefonoR.length < 9) {
                    binding.telefonoNewR.error =
                        "Il numero di telefono deve contenere almeno 9 caratteri."
                }

                val vegan: Boolean = veganR.isChecked

                if (nomeR.isNotEmpty() && nomeR.length < 35
                    && descrizioneR.isNotEmpty()
                    && descrizioneR.length > 50 && descrizioneR.length < 250
                    && indirizzoR.isNotEmpty() && indirizzoR.length > 15
                    && orariolavorativoR.isNotEmpty() && orariolavorativoR.length > 10
                    && telefonoR.isNotEmpty() && telefonoR.length > 8
                ) {
                    getUserData(object : FireBaseCallbackUser {
                        override fun onResponse(responseU: ResponseUser) {

                            if (responseU.user!!.Livello == "1") {
                                val childUpdates = hashMapOf<String, Any>(
                                    "Livello" to "3"
                                )
                                updateUserData(
                                    context, childUpdates
                                )
                            }

                            restaurantData = Restaurant(
                                "Restaurants-images/" + fileName,
                                nomeR,
                                descrizioneR,
                                indirizzoR,
                                orariolavorativoR,
                                telefonoR,
                                tipoCiboR,
                                vegan,
                                "1.0",
                                UUID.randomUUID().toString(),
                                responseU.user!!.Email
                            )
                            createRestaurant(context, restaurantData)

                            getRestaurantData(object : FireBaseCallbackRestaurant {
                                override fun onResponse(responseR: ResponseRistorante) {
                                    val intent =
                                        Intent(
                                            context,
                                            CreaMenu::class.java
                                        ).apply {
                                            putExtra("ristorante", restaurantData)
                                            putExtra("user", user)
                                        }
                                    startActivity(intent)
                                    activity?.finish()
                                }
                            }, context)
                        }
                    }, context)
                } else {
                    Toast.makeText(context, "Nessun campo può essere vuoto!", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                Toast.makeText(
                    context,
                    "Carica un'immagine prima di proseguire.",
                    Toast.LENGTH_LONG
                ).show()
            }


        }
    }

    override fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")

    override fun uploadImage() {
        fileName = UUID.randomUUID().toString() + ".jpg"

        val refStorage =
            FirebaseStorage.getInstance().getReference("Restaurants-images/").child(fileName)

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
}


