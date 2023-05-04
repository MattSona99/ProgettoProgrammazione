package com.example.progettoprogrammazione.dipendente

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.FragmentDLavoroBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackDipendente
import com.example.progettoprogrammazione.firebase.FireBaseCallbackOrder
import com.example.progettoprogrammazione.models.Dipendente
import com.example.progettoprogrammazione.models.Order
import com.example.progettoprogrammazione.models.User
import com.example.progettoprogrammazione.ordini.OrderAdapter
import com.example.progettoprogrammazione.ordini.OrderClickListener
import com.example.progettoprogrammazione.utils.DipendenteUtils
import com.example.progettoprogrammazione.utils.OrderUtils
import com.example.progettoprogrammazione.utils.ResponseDipendente
import com.example.progettoprogrammazione.utils.ResponseOrder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONArray
import org.json.JSONException


class FragmentLavoro : Fragment(), OrderUtils, DipendenteUtils, OrderClickListener {

    private lateinit var binding: FragmentDLavoroBinding
    private lateinit var adapter: OrderAdapter

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    lateinit var textView: TextView
    private lateinit var orderArrayList: ArrayList<Order>
    lateinit var dip: Dipendente


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDLavoroBinding.inflate(layoutInflater)
        orderArrayList = arrayListOf()
        val args = this.arguments
        val user = args?.getParcelable<User>("user") as User
        getDipendenteData(user.Email!!, object : FireBaseCallbackDipendente {
            override fun onResponse(responseD: ResponseDipendente) {
                dip = responseD.dipendenti.first()
                getOrders(dip, object : FireBaseCallbackOrder {
                    override fun onResponse(responseO: ResponseOrder) {
                        orderArrayList = responseO.ordini
                        val layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        binding.productList.layoutManager = layoutManager
                        adapter = OrderAdapter(orderArrayList, this@FragmentLavoro)
                        binding.productList.adapter = adapter
                        binding.productList.setHasFixedSize(true)
                        adapter.notifyDataSetChanged()
                    }

                }, context)
            }
        }, context)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnQr.setOnClickListener {
            val intentIntegrator = IntentIntegrator.forSupportFragment(this)
            intentIntegrator.setBeepEnabled(false)
            intentIntegrator.setPrompt("SCAN")
            intentIntegrator.setOrientationLocked(true)
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            intentIntegrator.initiateScan()
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(activity, "Scan fallito", Toast.LENGTH_LONG).show()
            } else {
                try {
                    val jsonOrdine = JSONArray(result.contents)
                    val jsonObject = jsonOrdine.getJSONObject(0)
                    val restID = jsonObject.getString("restID").toString()
                    if (dip.codiceRistorante == restID) {
                        createOrder(restID, jsonOrdine, context)
                    } else {
                        Toast.makeText(
                            context,
                            "Non hai i permessi per effettuare ordini in questo ristorante.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(activity, result.contents, Toast.LENGTH_LONG).show()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onClickOrder(order: Order) {
        val bundle = Bundle()
        bundle.putParcelableArrayList("ordini", orderArrayList)
        bundle.putString("numero", order.numero.toString())
        view?.findNavController()?.navigate(R.id.LavoroToOrderDetail, bundle)
    }


}