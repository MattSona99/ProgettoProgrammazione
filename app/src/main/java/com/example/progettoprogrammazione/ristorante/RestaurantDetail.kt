package com.example.progettoprogrammazione.ristorante

import android.app.AlertDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.activity.RestaurateurActivity
import com.example.progettoprogrammazione.databinding.Fragment1RestaurantDetailBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackProdotto
import com.example.progettoprogrammazione.firebase.FireBaseCallbackRating
import com.example.progettoprogrammazione.firebase.FireBaseCallbackRestaurant
import com.example.progettoprogrammazione.firebase.FireBaseCallbackUser
import com.example.progettoprogrammazione.models.Product
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.prodotti.ProductAdapter
import com.example.progettoprogrammazione.utils.*
import com.example.progettoprogrammazione.viewmodels.CartViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.File

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
/* Questa classe consente di adattare i campi di un ristorante specifico e mostrarli a schermo,
inoltre permette di visualizzare il menu ed aggiungere i prodotti al carrello utente;
infine, permette anche di effettuare il rating del ristorante attraverso un voto */

class RestaurantDetail : Fragment(), ProductUtils, RestaurantUtils,
    UserUtils {

    private lateinit var binding: Fragment1RestaurantDetailBinding

    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var bevandeArrayList: ArrayList<Product>
    private lateinit var antipastiArrayList: ArrayList<Product>
    private lateinit var primiArrayList: ArrayList<Product>
    private lateinit var secondiArrayList: ArrayList<Product>
    private lateinit var contorniArrayList: ArrayList<Product>
    private lateinit var dolciArrayList: ArrayList<Product>


    private lateinit var cartViewModel: CartViewModel
    private val cartViewModelR: CartViewModel by navGraphViewModels(R.id.nav_restaurateur)
    private val cartViewModelU: CartViewModel by navGraphViewModels(R.id.nav_user)
    private val cartViewModelD: CartViewModel by navGraphViewModels(R.id.nav_dipendente)

    private lateinit var uid: String
    private var restaurantList: ArrayList<Restaurant>? = null
    private var restaurant: Restaurant? = null
    private lateinit var adapter: ProductAdapter

    private lateinit var user: FirebaseAuth
    private lateinit var userlvl: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = Fragment1RestaurantDetailBinding.inflate(layoutInflater)
        binding.optionsRest.isGone = true
        binding.btnModificaMenu.isGone = true

        val args = this.arguments
        val restaurantID = args?.get("restID")
        restaurantList = args?.getParcelableArrayList("restArrayList")
        userlvl = args?.get("userlvl") as String

        user = FirebaseAuth.getInstance()
        uid = user.currentUser!!.uid

        when (userlvl) {
            "1" -> cartViewModel = cartViewModelU
            "2" -> cartViewModel = cartViewModelD
            "3" -> cartViewModel = cartViewModelR
        }

        // Mostra i bottoni di modifica del ristorante solo se l'utente loggato è il proprietario
        restaurant = restaurantFromId(restaurantID.toString())
        if (userlvl == "3" && restaurant!!.proprietarioR==user.currentUser!!.email.toString()) {
            binding.optionsRest.isVisible = true
            binding.btnModificaMenu.isVisible = true
        }

        if (restaurant != null) {
            binding.btnModificaRistorante.isVisible =
                restaurant?.proprietarioR == user.currentUser?.email
            binding.btnEliminaRistorante.isVisible =
                restaurant?.proprietarioR == user.currentUser?.email
        }

        if (restaurant != null) {
            val imageName = restaurant?.imageR
            val storageRef = FirebaseStorage.getInstance().reference.child("$imageName")
            val localfile = File.createTempFile("tempImage", "jpg")
            storageRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                binding.imgRistoranteDetail.setImageBitmap(bitmap)
            }.addOnFailureListener {
                Toast.makeText(context, "Caricamento immagine fallito", Toast.LENGTH_SHORT).show()
            }

            binding.nomeRistoranteDetail.text =
                restaurant?.nomeR!!.substring(0, 1).uppercase() + restaurant?.nomeR!!.substring(1)
            binding.descrizioneDetail.text = restaurant?.descrizioneR!!.substring(0, 1)
                .uppercase() + restaurant?.descrizioneR!!.substring(1)
            binding.ratingdetail.text = restaurant?.ratingR.toString()
            binding.numeroDetail.text = restaurant?.telefonoR
            binding.indirizzodetail.text = restaurant?.indirizzoR
            binding.cucinadetail.text = restaurant?.tipoCiboR
        }

        // Queste funzioni permettono di adattare i prodotti nel menu del ristorante a seconda delle tipologie
        getProdotti(
            restaurantID.toString(),
            object : FireBaseCallbackProdotto {
                override fun onResponse(responseP: ResponseProdotto) {
                    bevandeArrayList = responseP.prodotto
                    bindrecyclerviews(bevandeArrayList, binding.recycleViewBevande)
                    binding.btnBevande.isChecked = true
                    binding.bevandeMenu.isVisible = true
                }
            }, "Bevande", context
        )
        getProdotti(
            restaurantID.toString(),
            object : FireBaseCallbackProdotto {
                override fun onResponse(responseP: ResponseProdotto) {
                    antipastiArrayList = responseP.prodotto
                }
            }, "Antipasti", context
        )
        getProdotti(
            restaurantID.toString(),
            object : FireBaseCallbackProdotto {
                override fun onResponse(responseP: ResponseProdotto) {
                    primiArrayList = responseP.prodotto
                }
            }, "Primi", context
        )
        getProdotti(
            restaurantID.toString(),
            object : FireBaseCallbackProdotto {
                override fun onResponse(responseP: ResponseProdotto) {
                    secondiArrayList = responseP.prodotto
                }
            }, "Secondi", context
        )
        getProdotti(
            restaurantID.toString(),
            object : FireBaseCallbackProdotto {
                override fun onResponse(responseP: ResponseProdotto) {
                    contorniArrayList = responseP.prodotto
                }
            }, "Contorni", context
        )
        getProdotti(
            restaurantID.toString(),
            object : FireBaseCallbackProdotto {
                override fun onResponse(responseP: ResponseProdotto) {
                    dolciArrayList = responseP.prodotto
                }
            }, "Dolci", context
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = this.arguments
        val restaurantID = args?.get("restID")
        restaurantList = args?.getParcelableArrayList("restArrayList")

        // Cliccando sul bottone, permette al proprietario di modificare il menu
        binding.btnModificaMenu.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelableArrayList("bevande", bevandeArrayList)
            bundle.putParcelableArrayList("antipasti", antipastiArrayList)
            bundle.putParcelableArrayList("primi", primiArrayList)
            bundle.putParcelableArrayList("secondi", secondiArrayList)
            bundle.putParcelableArrayList("contorni", contorniArrayList)
            bundle.putParcelableArrayList("dolci", dolciArrayList)
            bundle.putString("idR", restaurantID.toString())

            view.findNavController().navigate(R.id.RestaurantDetailToModificaMenu, bundle)
        }

        // Queste funzioni permettono di visualizzare le recycler views inerenti al bottone cliccato
        binding.btnBevande.setOnClickListener {
            invisible()
            bindrecyclerviews(bevandeArrayList, binding.recycleViewBevande)
            binding.bevandeMenu.isVisible = true
        }

        binding.btnAntipasti.setOnClickListener {
            invisible()
            bindrecyclerviews(antipastiArrayList, binding.recycleViewAntipasti)
            binding.antipastiMenu.isVisible = true
        }

        binding.btnPrimi.setOnClickListener {
            invisible()
            bindrecyclerviews(primiArrayList, binding.recycleViewPrimi)
            binding.primiMenu.isVisible = true
        }

        binding.btnSecondi.setOnClickListener {
            invisible()
            bindrecyclerviews(secondiArrayList, binding.recycleViewSecondi)
            binding.secondiMenu.isVisible = true
        }

        binding.btnContorni.setOnClickListener {
            invisible()
            bindrecyclerviews(contorniArrayList, binding.recycleViewContorni)
            binding.contorniMenu.isVisible = true
        }

        binding.btnDolci.setOnClickListener {
            invisible()
            bindrecyclerviews(dolciArrayList, binding.recycleViewDolci)
            binding.dolciMenu.isVisible = true
        }

        // Cliccando questo bottone, permette al proprietario di eliminare il ristorante
        binding.btnEliminaRistorante.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            val reference = firebaseDatabase.getReference("Ristoranti")
            val idR = restaurant?.idR
            builder.setTitle("Conferma l'eliminazione del ristorante")
            builder.setMessage("Sei sicuro di voler eliminare il ristorante?")
            builder.setPositiveButton("Sì") { _, _ ->
                reference.child("$idR").removeValue()
                Toast.makeText(context, "Ristorante eliminato con successo.", Toast.LENGTH_SHORT).show()
                getUserData(object : FireBaseCallbackUser {
                    override fun onResponse(responseU: ResponseUser) {
                        getRestaurantData(object : FireBaseCallbackRestaurant {
                            override fun onResponse(responseR: ResponseRistorante) {
                                val intent = Intent(context, RestaurateurActivity::class.java)
                                intent.putExtra("ristoranti", responseR.ristoranti)
                                intent.putExtra("user", responseU.user)
                                startActivity(intent)
                                activity?.finish()
                            }
                        }, context)
                    }
                }, context)

            }
            builder.setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
            }
            builder.show()
        }

        // Cliccando questo bottone, permette al proprietario di modificare il ristorante
        binding.btnModificaRistorante.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("restID", restaurant?.idR.toString())
            view.findNavController().navigate(R.id.DetailToModifica, bundle)
        }

        // Cliccando questa barra, permette all'utente di recensire il ristorante
        binding.ratingRistorante.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "La tua valutazione: " + binding.ratingBarR.rating,
                Toast.LENGTH_SHORT
            ).show()
            val ratingR = firebaseDatabase.getReference("Ristoranti/$restaurantID/ratingR")

            firebaseDatabase.getReference("Utenti/$uid/ratings").child(restaurant?.nomeR!!)
                .setValue(binding.ratingBarR.rating)

            firebaseDatabase.getReference("Ristoranti/$restaurantID/usersRatings")
                .child(user.currentUser?.uid!!).setValue(binding.ratingBarR.rating)


            getRating(object : FireBaseCallbackRating {
                override fun onResponse(responseR: ResponseRating) {

                    val sum = responseR.rating.sum()
                    val avg = sum / responseR.rating.size
                    ratingR.setValue(avg)
                }
            }, context, restaurantID.toString())

        }
    }

    // Restituisce un ristorante se l'id corrisponde al parametro passato
    private fun restaurantFromId(restaurantID: String?): Restaurant? {
        for (restaurant in restaurantList!!) {
            if (restaurant.idR == restaurantID)
                return restaurant
        }
        return null
    }

    // Ripulisce parte dei componenti grafici al fine di nascondere informazioni
    private fun invisible() {
        binding.tutteMenu.isGone = false
        binding.bevandeMenu.isGone = true
        binding.antipastiMenu.isGone = true
        binding.primiMenu.isGone = true
        binding.secondiMenu.isGone = true
        binding.contorniMenu.isGone = true
        binding.dolciMenu.isGone = true
    }

    // Effettua il bind delle recycler view a seconda della lista passata come parametro
    private fun bindrecyclerviews(
        prodotti: ArrayList<Product>,
        recyclerView: RecyclerView
    ) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        adapter = ProductAdapter(prodotti, requireContext(), cartViewModel, restaurant!!.idR.toString())
        showData(prodotti)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        adapter.notifyDataSetChanged()
    }

    private fun showData(arrayList: ArrayList<Product>) {
        adapter.setData(arrayList)
    }

}