package com.example.progettoprogrammazione.ristorante


import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.FragmentRestaurantDetailBinding
import com.example.progettoprogrammazione.models.RESTAURANT_EXTRA
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

        //PRENDO DATI RISTORANTE DAL BUNDLE
        val args = this.arguments
        val restaurantID = args?.get("restID")
        restaurantList = args?.getParcelableArrayList<Restaurant>("restArrayList")

        val restaurant=restaurantFromId(restaurantID.toString().toInt())

        if(restaurant != null)
        {
            binding.imgRistoranteDetail.setImageResource(restaurant.image_r)
            binding.nomeRistoranteDetail.text = restaurant.nome_r
            binding.descrizioneDetail.text = restaurant.descrizioneR

        }
        return binding.root
    }

    private fun restaurantFromId(restaurantID: Int?): Restaurant? {
        for (restaurant in restaurantList!!) {
            if(restaurant.id == restaurantID)
                return restaurant
        }
        return null
    }
}