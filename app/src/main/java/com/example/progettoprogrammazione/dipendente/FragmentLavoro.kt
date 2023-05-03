package com.example.progettoprogrammazione.dipendente

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.progettoprogrammazione.databinding.FragmentLavoroBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackDipendente
import com.example.progettoprogrammazione.firebase.FireBaseCallbackOrder
import com.example.progettoprogrammazione.models.Dipendente
import com.example.progettoprogrammazione.models.Order
import com.example.progettoprogrammazione.models.User
import com.example.progettoprogrammazione.utils.DipendenteUtils
import com.example.progettoprogrammazione.utils.OrderUtils
import com.example.progettoprogrammazione.utils.ResponseDipendente
import com.example.progettoprogrammazione.utils.ResponseOrder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONArray
import org.json.JSONException


class FragmentLavoro : Fragment(), OrderUtils, DipendenteUtils {

    private lateinit var binding: FragmentLavoroBinding

    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    lateinit var textView: TextView
    private lateinit var orderArrayList: ArrayList<Order>
    lateinit var dip: Dipendente


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLavoroBinding.inflate(layoutInflater)
        orderArrayList = arrayListOf()
        val args = this.arguments
        val user = args?.getParcelable<User>("user") as User
        getDipendenteData(user.Email!!, object: FireBaseCallbackDipendente{
            override fun onResponse(responseD: ResponseDipendente) {
                dip = responseD.dipendenti.first()
                getOrders(dip, object : FireBaseCallbackOrder{
                    override fun onResponse(responseO: ResponseOrder) {
                        orderArrayList = responseO.ordini
                    }

                }, context)
            }
        }, context)


      //  getOrders()


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
                    val o = JSONArray(result.contents)
                    val jsonString = o.getJSONObject(0)
                    createOrder(jsonString, context)

                } catch (e: JSONException) {
                    e.printStackTrace()

                    Toast.makeText(activity, result.contents, Toast.LENGTH_LONG).show()
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }



}