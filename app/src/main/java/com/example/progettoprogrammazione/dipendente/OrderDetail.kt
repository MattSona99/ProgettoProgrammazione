package com.example.progettoprogrammazione.dipendente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.progettoprogrammazione.databinding.FragmentDOrderDetailBinding
import com.example.progettoprogrammazione.models.Order
import com.example.progettoprogrammazione.ordini.OrderProductAdapter
import org.json.JSONArray

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Questo fragment mostra in una recyclerview i dettagli di un ordine

class OrderDetail : Fragment() {

    private lateinit var binding: FragmentDOrderDetailBinding

    private lateinit var adapter: OrderProductAdapter

    private var orderList: ArrayList<Order>? = null
    private var order: Order? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = FragmentDOrderDetailBinding.inflate(layoutInflater)

        val args = this.arguments
        val numero = args?.getString("numero")
        orderList = args?.getParcelableArrayList("ordini")

        order = orderFromNumber(numero)
        binding.nOrderD.text = "Ordine numero: " + order?.numero.toString()

        // Adatta i dettagli dell'ordine in un ArrayList<HashMap<String, String>> per inserirla in una recyclerview

        val jsonProducts = JSONArray(order?.json)

        val products = arrayListOf<HashMap<String, String>>()
        for(i in 0 until jsonProducts.length()) {
            val jsonObject = jsonProducts.getJSONObject(i)
            val hashMap = hashMapOf<String, String>()
            hashMap["pname"] = jsonObject.getString("pname")
            hashMap["quantity"] = jsonObject.getString("quantity")
            hashMap["numero"] = numero.toString()
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

    // Restituisce l'ordine relativo al numero passato come parametro

    private fun orderFromNumber(numero: String?): Order? {
        for (order in orderList!!) {
            if (order.numero.toString() == numero)
                return order
        }
        return null
    }

}