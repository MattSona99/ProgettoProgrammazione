package com.example.progettoprogrammazione.ristorante

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.progettoprogrammazione.databinding.FragmentRestaurantDetailBinding
import com.example.progettoprogrammazione.models.Restaurant
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class RestaurantDetail : Fragment(){

    private lateinit var binding: FragmentRestaurantDetailBinding

    private var restaurantList : ArrayList<Restaurant>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentRestaurantDetailBinding.inflate(layoutInflater)

        val args = this.arguments
        val restaurantID = args?.get("restID")
        restaurantList = args?.getParcelableArrayList("restArrayList")

        val restaurant=restaurantFromId(restaurantID.toString())

        if(restaurant != null)
        {
            val imageName = restaurant.imageR
            val storageRef = FirebaseStorage.getInstance().reference.child("$imageName")
            val localfile = File.createTempFile("tempImage","jpg")
            storageRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                binding.imgRistoranteDetail.setImageBitmap(bitmap)
            }.addOnFailureListener {
                Toast.makeText(context, "Caricamento immagine fallito",Toast.LENGTH_SHORT).show()
            }

//            binding.imgRistoranteDetail.setImageResource( getImageId( binding.root.context,restaurant.imageR!!))
            binding.nomeRistoranteDetail.text = restaurant.nomeR
            binding.descrizioneDetail.text = restaurant.descrizioneR

        }

        binding.visualizzaMenu.setOnClickListener{

        }

        return binding.root
    }

    private fun getImageId(context: Context, imageName: String): Int {
        return context.resources
            .getIdentifier("drawable/$imageName", null, context.packageName)
    }

    private fun restaurantFromId(restaurantID: String?): Restaurant? {
        for (restaurant in restaurantList!!) {
            if(restaurant.idR == restaurantID)
                return restaurant
        }
        return null
    }

}