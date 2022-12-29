package com.example.progettoprogrammazione.prodotti

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.progettoprogrammazione.databinding.FragmentMenuBinding
import com.example.progettoprogrammazione.models.Product

class ProductDetail : Fragment() {

    private lateinit var binding: FragmentMenuBinding

    private var productList: ArrayList<Product>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = FragmentMenuBinding.inflate(layoutInflater)

        val args = this.arguments
        val productID = args?.get("productID")
        productList = args?.getParcelableArrayList("productArrayList")

        val prodotti = producttFromId(productID.toString())

        if (prodotti != null) {


        }
        return binding.root
    }

    private fun producttFromId(productID: String?): Product? {
        for (prodotti in productList!!) {
            if (prodotti.idP == productID)
                return prodotti
        }
        return null
    }
}