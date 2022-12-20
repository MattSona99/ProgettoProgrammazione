package com.example.progettoprogrammazione.ristorante


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.progettoprogrammazione.databinding.FragmentRestaurantDetailBinding
import com.example.progettoprogrammazione.models.Restaurant


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

        val restaurant=restaurantFromId(restaurantID.toString().toInt())

        if(restaurant != null)
        {
            binding.imgRistoranteDetail.setImageResource( getImageId( binding.root.context,restaurant.imageR!!))
            binding.nomeRistoranteDetail.text = restaurant.nomeR
            binding.descrizioneDetail.text = restaurant.descrizioneR

        }
        return binding.root
    }

    private fun getImageId(context: Context, imageName: String): Int {
        return context.resources
            .getIdentifier("drawable/$imageName", null, context.packageName)
    }

    private fun restaurantFromId(restaurantID: Int?): Restaurant? {
        for (restaurant in restaurantList!!) {
            if(restaurant.id == restaurantID)
                return restaurant
        }
        return null
    }
}