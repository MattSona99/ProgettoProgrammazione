package com.example.progettoprogrammazione.dipendente

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.activity.EmployeeActivity
import com.example.progettoprogrammazione.databinding.FragmentDLavoroBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackDipendente
import com.example.progettoprogrammazione.firebase.FireBaseCallbackOrder
import com.example.progettoprogrammazione.firebase.FireBaseCallbackUser
import com.example.progettoprogrammazione.models.Dipendente
import com.example.progettoprogrammazione.models.Order
import com.example.progettoprogrammazione.models.User
import com.example.progettoprogrammazione.ordini.OrderAdapter
import com.example.progettoprogrammazione.ordini.OrderClickListener
import com.example.progettoprogrammazione.utils.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONArray
import org.json.JSONException

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
// Questo fragment permette al dipendente di controllare gli ordini e scannerizzare un QR-Code

class FragmentLavoro : Fragment(), OrderUtils, DipendenteUtils, OrderClickListener, UserUtils {

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
        binding.ConstraintLavoro.isGone = true
        binding.zeroOrders.isGone = true
        orderArrayList = arrayListOf()
        val args = this.arguments
        val user = args?.getParcelable<User>("user") as User

        /* Richiama la funzione per cercare gli ordini relativi dello stesso ristorante in cui
        lavora il dipendente e li inserisce in una recycler view*/
        getDipendenteData(user.Email!!, object : FireBaseCallbackDipendente {
            override fun onResponse(responseD: ResponseDipendente) {
                dip = responseD.dipendenti.first()
                getOrders(dip, object : FireBaseCallbackOrder {
                    override fun onResponse(responseO: ResponseOrder) {
                        orderArrayList = responseO.ordini
                        if (orderArrayList.isNotEmpty()) {
                            binding.ConstraintLavoro.isVisible = true
                            binding.zeroOrders.isGone = true
                            val layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            binding.productList.layoutManager = layoutManager
                            adapter = OrderAdapter(orderArrayList, this@FragmentLavoro)
                            binding.productList.adapter = adapter
                            binding.productList.setHasFixedSize(true)
                            adapter.notifyDataSetChanged()
                        } else {
                            binding.ConstraintLavoro.isGone = true
                            binding.zeroOrders.isVisible = true
                        }
                    }
                }, context)
            }
        }, context)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Scannerizza il QR-Code da uno scanner quando si clicca sul bottone
        binding.btnQr.setOnClickListener {
            val intentIntegrator = IntentIntegrator.forSupportFragment(this)
            intentIntegrator.setBeepEnabled(false)
            intentIntegrator.setPrompt("SCAN")
            intentIntegrator.setOrientationLocked(true)
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            intentIntegrator.initiateScan()
        }

        // Elimina gli ordini (una volta ultimati) al fine di liberare spazio
        binding.ConstraintLavoro.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("ATTENZIONE!!!")
            builder.setMessage("Questa è un'operazione che va eseguita solo da dipendenti autorizzati. Vuoi procedere con l'eliminazione degli ordini?")
            builder.setPositiveButton("Sì") { _, _ ->
                deleteOrders(dip.codiceRistorante.toString(), context)
                getUserData(object : FireBaseCallbackUser {
                    override fun onResponse(responseU: ResponseUser) {
                        val intent = Intent(context, EmployeeActivity::class.java)
                        intent.putExtra("user", responseU.user)
                        startActivity(intent)
                        activity?.finish()
                    }
                }, context)
            }
            builder.setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
            }
            builder.show()
        }
    }

    // Quando l'activity restituisce un risultato dallo scanner, crea l'ordine sul database
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

    // Quando viene effettuato un click su un ordine, si effettuerà la navigazione verso i suoi dettagli
    override fun onClickOrder(order: Order) {
        val bundle = Bundle()
        bundle.putParcelableArrayList("ordini", orderArrayList)
        bundle.putString("numero", order.numero.toString())
        view?.findNavController()?.navigate(R.id.LavoroToOrderDetail, bundle)
    }


}