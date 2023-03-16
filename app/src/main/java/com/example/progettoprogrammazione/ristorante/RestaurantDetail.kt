package com.example.progettoprogrammazione.ristorante

import android.app.AlertDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.activity.RestaurateurActivity
import com.example.progettoprogrammazione.databinding.FragmentRestaurantDetailBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackProdotto
import com.example.progettoprogrammazione.firebase.FireBaseCallbackRating
import com.example.progettoprogrammazione.firebase.FireBaseCallbackRestaurant
import com.example.progettoprogrammazione.firebase.FireBaseCallbackUser
import com.example.progettoprogrammazione.models.Product
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.prodotti.ProductClickListener
import com.example.progettoprogrammazione.utils.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class RestaurantDetail : Fragment(), ProductClickListener, ProductUtils, RestaurantUtils,
    UserUtils {

    private lateinit var binding: FragmentRestaurantDetailBinding

    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var bevandeArrayList: ArrayList<Product>
    private lateinit var antipastiArrayList: ArrayList<Product>
    private lateinit var primiArrayList: ArrayList<Product>
    private lateinit var secondiArrayList: ArrayList<Product>
    private lateinit var contorniArrayList: ArrayList<Product>
    private lateinit var dolciArrayList: ArrayList<Product>

    private lateinit var uid: String
    private var restaurantList: ArrayList<Restaurant>? = null
    private var restaurant: Restaurant? = null

    private lateinit var user: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = FragmentRestaurantDetailBinding.inflate(layoutInflater)

        val args = this.arguments
        val restaurantID = args?.get("restID")
        restaurantList = args?.getParcelableArrayList("restArrayList")

        user = FirebaseAuth.getInstance()
        uid = user.currentUser!!.uid


        restaurant = restaurantFromId(restaurantID.toString())

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
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = this.arguments
        val restaurantID = args?.get("restID")
        restaurantList = args?.getParcelableArrayList("restArrayList")


        getBevanda(
            restaurantID.toString(),
            object : FireBaseCallbackProdotto {
                override fun onResponse(responseP: ResponseProdotto) {
                    bevandeArrayList = responseP.prodotto
                }
            }, context
        )
        getAntipasto(
            restaurantID.toString(),
            object : FireBaseCallbackProdotto {
                override fun onResponse(responseP: ResponseProdotto) {
                    antipastiArrayList = responseP.prodotto
                }
            }, context
        )
        getPrimo(
            restaurantID.toString(),
            object : FireBaseCallbackProdotto {
                override fun onResponse(responseP: ResponseProdotto) {
                    primiArrayList = responseP.prodotto
                }
            }, context
        )
        getSecondo(
            restaurantID.toString(),
            object : FireBaseCallbackProdotto {
                override fun onResponse(responseP: ResponseProdotto) {
                    secondiArrayList = responseP.prodotto
                }
            }, context
        )
        getContorno(
            restaurantID.toString(),
            object : FireBaseCallbackProdotto {
                override fun onResponse(responseP: ResponseProdotto) {
                    contorniArrayList = responseP.prodotto
                }
            }, context
        )
        getDolce(
            restaurantID.toString(),
            object : FireBaseCallbackProdotto {
                override fun onResponse(responseP: ResponseProdotto) {
                    dolciArrayList = responseP.prodotto
                }
            }, context
        )

        binding.visualizzaMenu.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelableArrayList("bevande", bevandeArrayList)
            bundle.putParcelableArrayList("antipasti", antipastiArrayList)
            bundle.putParcelableArrayList("primi", primiArrayList)
            bundle.putParcelableArrayList("secondi", secondiArrayList)
            bundle.putParcelableArrayList("contorni", contorniArrayList)
            bundle.putParcelableArrayList("dolci", dolciArrayList)
            bundle.putString("proprietarioR",restaurant?.proprietarioR.toString())
            bundle.putParcelableArrayList("restArrayList", restaurantList)

            view.findNavController().navigate(R.id.DetailToMenu, bundle)
        }

        binding.eliminaRistorante.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            val reference = firebaseDatabase.getReference("Ristoranti")
            val idR = restaurant?.idR
            builder.setTitle("Conferma l'eliminazione del ristorante")
            builder.setMessage("Sei sicuro di voler eliminare il ristorante?")
            builder.setPositiveButton("Sì") { dialog, _ ->
                reference.child("$idR").removeValue()
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

        binding.modificaRistorante.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("restID", restaurant?.idR.toString())
            view.findNavController().navigate(R.id.DetailToModifica, bundle)
        }

        binding.btnRating.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "La tua valutazione: " + binding.ratingBarR.rating,
                Toast.LENGTH_SHORT
            ).show()
            var ratingR = firebaseDatabase.getReference("Ristoranti/$restaurantID/ratingR")

            firebaseDatabase.getReference("Utenti/$uid/ratings").child(restaurant?.nomeR!!)
                .setValue(binding.ratingBarR.rating)

            firebaseDatabase.getReference("Ristoranti/$restaurantID/usersRatings")
                .child(user.currentUser?.uid!!).setValue(binding.ratingBarR.rating)


            getRating(object : FireBaseCallbackRating {
                override fun onResponse(responseR: ResponseRating) {

                    var sum = responseR.rating.sum()
                    var avg = sum / responseR.rating.size
                    ratingR.setValue(avg)
                }
            }, context, restaurantID.toString())

        }
    }

    override fun onClickProduct(prodotto: Product) {

        val bundle1 = Bundle()
        bundle1.putString("prodID", prodotto.idP.toString())
        bundle1.putParcelableArrayList("prodArrayList", bevandeArrayList)

        view?.findNavController()?.navigate(R.id.productDetail, bundle1)
    }

    private fun restaurantFromId(restaurantID: String?): Restaurant? {
        for (restaurant in restaurantList!!) {
            if (restaurant.idR == restaurantID)
                return restaurant
        }
        return null
    }

}