package com.example.progettoprogrammazione.dipendente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.progettoprogrammazione.databinding.FragmentOrderDetailBinding
import com.example.progettoprogrammazione.models.Order
import com.example.progettoprogrammazione.ordini.OrderProductAdapter
import org.json.JSONArray

class OrderDetail : Fragment() {

    private lateinit var binding: FragmentOrderDetailBinding

    private lateinit var adapter: OrderProductAdapter

    private var orderList: ArrayList<Order>? = null
    private var order: Order? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = FragmentOrderDetailBinding.inflate(layoutInflater)

        val args = this.arguments
        val numero = args?.getString("numero")
        orderList = args?.getParcelableArrayList("ordini")

        order = orderFromNumber(numero)
        binding.nOrderD.text = "Ordine numero: " + order?.numero.toString()

        val jsonProducts = JSONArray(order?.json)

        val products = arrayListOf<HashMap<String, String>>()
        for(i in 0 until jsonProducts.length()) {
            val jsonObject = jsonProducts.getJSONObject(i)
            val hashMap = hashMapOf<String, String>()
            hashMap["pname"] = jsonObject.getString("pname")
            hashMap["quantity"] = jsonObject.getString("quantity")
            products.add(hashMap)
        }

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.productListD.layoutManager = layoutManager
        adapter = OrderProductAdapter(products)
        binding.productListD.adapter = adapter
        binding.productListD.setHasFixedSize(true)
        adapter.notifyDataSetChanged()

        return binding.root
    }

    private fun orderFromNumber(numero: String?): Order? {
        for (order in orderList!!) {
            if (order.numero.toString() == numero)
                return order
        }
        return null
    }

}