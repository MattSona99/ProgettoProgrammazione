package com.example.progettoprogrammazione.fragment

import android.os.Bundle
import android.provider.ContactsContract.Data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.FragmentMenuBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackProdotto
import com.example.progettoprogrammazione.models.Product
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.prodotti.ProductAdapter
import com.example.progettoprogrammazione.prodotti.ProductClickListener
import com.example.progettoprogrammazione.utils.ProductUtils
import com.example.progettoprogrammazione.utils.ResponseProdotto
import com.example.progettoprogrammazione.viewmodels.ProductViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FragmentMenu : Fragment(), ProductClickListener, ProductUtils {

    private lateinit var binding: FragmentMenuBinding
    private lateinit var adapter: ProductAdapter

    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    private var restaurantList: ArrayList<Restaurant>? = null

    private lateinit var productDataViewModel: ProductViewModel
    private lateinit var prodArrayList: ArrayList<Product>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(layoutInflater)

        val args = this.arguments
        val restaurantID = args?.get("restID")
        restaurantList = args?.getParcelableArrayList("restArrayList")

        prodArrayList= args?.getParcelableArrayList("prodotti")!!

        val layoutManager = GridLayoutManager(context, 1)
        binding.recycleViewP.layoutManager = layoutManager
        adapter = ProductAdapter(prodArrayList, this)
        binding.recycleViewP.adapter = adapter
        binding.recycleViewP.setHasFixedSize(true)
        adapter.notifyDataSetChanged()

        /*
        productDataViewModel= ViewModelProvider(requireActivity())[ProductViewModel::class.java]
        productDataViewModel.arrayListaprodottiLiveData.observe(viewLifecycleOwner){

            prodArrayList = it
            val layoutManager = GridLayoutManager(context, 1)
            binding.recycleViewP.layoutManager = layoutManager
            adapter = ProductAdapter(it, this)
            binding.recycleViewP.adapter = adapter
            binding.recycleViewP.setHasFixedSize(true)
            adapter.notifyDataSetChanged()
        }
        */

        return binding.root


    }

    override fun onClickProduct(prodotto: Product) {

        val bundle = Bundle()
        bundle.putString("prodID", prodotto.idP.toString())
        bundle.putParcelableArrayList("prodArrayList", prodArrayList)

        view?.findNavController()?.navigate(R.id.productDetail, bundle)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}