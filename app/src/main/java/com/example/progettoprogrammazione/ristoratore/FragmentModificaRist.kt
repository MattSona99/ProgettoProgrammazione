package com.example.progettoprogrammazione.ristoratore

import android.app.AlertDialog
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.progettoprogrammazione.databinding.FragmentModificaRistBinding
import com.example.progettoprogrammazione.databinding.FragmentProfiloBinding
import com.example.progettoprogrammazione.utils.ImgUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import kotlin.collections.HashMap

class FragmentModificaRist : Fragment(), ImgUtils {

    private lateinit var binding: FragmentModificaRistBinding

    private var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    private var imageUri: Uri? = null
    private var newimg: String? = null

    private lateinit var tipocibo: TextView

    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri = uri
            }
        }
    lateinit var fileName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentModificaRistBinding.inflate(layoutInflater)
        tipocibo = binding.newTipocibo
        val typearray =
            arrayOf("Italiano", "Cinese", "Giapponese", "Indiano", "Greco", "Pizza", "Burger")
        arrayListOf<String>()
        val selected = BooleanArray(typearray.size)

        binding.newTipocibo.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Seleziona il tipo di cibo")

            val selectedItems = mutableListOf(*typearray)

            builder.setCancelable(false)

            builder.setMultiChoiceItems(typearray, selected) { _, which, isChecked ->
                selected[which] = isChecked
                selectedItems[which]
            }

            builder.setPositiveButton("Ok") { _, _ ->
                binding.newTipocibo.text = ""
                for (i in selected.indices) {
                    if (selected[i]) {
                        binding.newTipocibo.text =
                            String.format("%s%s, ", binding.newTipocibo.text, selectedItems[i])
                    }
                }
                binding.newTipocibo.text =
                    binding.newTipocibo.text.substring(0, binding.newTipocibo.text.length - 2)
            }
            builder.setNegativeButton("Annulla") { dialog, _ ->
                dialog.cancel()
            }
            builder.setNeutralButton("Pulisci") { _: DialogInterface?, _: Int ->
                Arrays.fill(selected, false)
                binding.newTipocibo.text = ""
            }
            builder.show()
        }

        binding.inizioOrarioScelto.isVisible = false
        binding.fineOrarioScelto.isVisible = false

        OnClickTime(binding.inizioOrarioScelto, binding.newInizioOrarioRist)
        OnClickTime(binding.fineOrarioScelto, binding.newFineOrarioRist)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = this.arguments
        val restaurantID = args?.get("restID")

        binding.newImg.setOnClickListener {
            selectImageFromGallery()
        }
        binding.modificaRist.setOnClickListener {

            val newnome = binding.newNomeristorante.text.toString()
            val newdescrizione = binding.newDescrizione.text.toString()
            val newindirizzo = binding.newIndirizzo.text.toString()
            val neworarioinizio = binding.inizioOrarioScelto.text.toString()
            val neworariofine = binding.fineOrarioScelto.text.toString()
            val newtel = binding.newTelefono.text.toString()
            val newtipocibo = binding.newTipocibo.text.toString()
            val newvegan = binding.newVegan
            if (imageUri != null) {
                uploadImage(imageUri!!)
                newimg = "Restaurants-images/" + fileName
            }


            if (newnome.length > 35) {
                binding.newNomeristorante.error =
                    "Il nome non può essere lungo più di 35 caratteri."
            }
            if (newdescrizione.length > 50 || newdescrizione.length < 10) {
                binding.newDescrizione.error =
                    "La descrizione deve essere compresa tra 50 e 250 caratteri"
            }
            if (newindirizzo.length < 10) {
                binding.newIndirizzo.error = "Indririzzo errato o vuoto."
            }
            if (newtel.length > 11) {
                binding.newTelefono.error =
                    "Il numero di telefono deve contenere al massimo 11 cifre."
            }

            val vegan: Boolean = newvegan.isChecked

            val childUpdates: HashMap<String, Any> = hashMapOf()
            if (newnome.isNotEmpty() && newnome.length < 35) childUpdates["nomeR"] = newnome
            if (newdescrizione.isNotEmpty() && newdescrizione.length < 50
                && newdescrizione.length > 10
            ) childUpdates["descrizioneR"] =
                newdescrizione
            if (newindirizzo.isNotEmpty() && newindirizzo.length > 10) childUpdates["indirizzoR"] =
                newindirizzo
            if (neworarioinizio.isNotEmpty()) childUpdates["orarioinizioR"] =
                neworarioinizio
            if (neworariofine.isNotEmpty()) childUpdates["orariofineR"] =
                neworariofine
            if (newtel.isNotEmpty() && newtel.length < 12) childUpdates["telefonoR"] = newtel
            if (newtipocibo.isNotEmpty()) childUpdates["tipoCiboR"] = newtipocibo
            if (newimg != null) childUpdates["imageR"] = newimg!!
            childUpdates["veganR"] = vegan

            firebaseDatabase.getReference("Ristoranti").child("$restaurantID")
                .updateChildren(childUpdates).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            context,
                            "Update effettuato con successo.",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    } else Toast.makeText(
                        context,
                        "Errore durante l'update dei dati.",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }


        }
    }

    private fun OnClickTime(textView: TextView, timePicker: TimePicker) {

        timePicker.setOnTimeChangedListener { _, hour, minute ->
            var hour = hour
            var am_pm = ""
            // AM_PM decider logic
            when {
                hour == 0 -> {
                    hour += 12
                    am_pm = "AM"
                }
                hour == 12 -> am_pm = "PM"
                hour > 12 -> {
                    hour -= 12
                    am_pm = "PM"
                }
                else -> am_pm = "AM"
            }

            val ora = if (hour < 10) "0" + hour else hour
            val min = if (minute < 10) "0" + minute else minute
            // display format of time
            val msg = "$ora : $min $am_pm"
            textView.text = msg

        }
    }

    override fun selectImageFromGallery() = selectImageFromGalleryResult.launch("image/*")

}