package com.example.progettoprogrammazione.ristorante

import android.app.AlertDialog
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.activity.IntroActivity
import com.example.progettoprogrammazione.databinding.FragmentRestaurantDetailBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackProdotto
import com.example.progettoprogrammazione.models.Product
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.models.User
import com.example.progettoprogrammazione.prodotti.ProductClickListener
import com.example.progettoprogrammazione.utils.ProductUtils
import com.example.progettoprogrammazione.utils.ResponseProdotto
import com.example.progettoprogrammazione.viewmodels.ProductViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class RestaurantDetail : Fragment(), ProductClickListener, ProductUtils {

    private lateinit var binding: FragmentRestaurantDetailBinding

    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    private lateinit var productDataViewModel: ProductViewModel

    private var bevandeArrayList: ArrayList<Product>? = null
    private var antipastiArrayList: ArrayList<Product>? = null
    private var primiArrayList: ArrayList<Product>? = null
    private var secondiArrayList: ArrayList<Product>? = null
    private var contornoArrayList: ArrayList<Product>? = null
    private var dolceArrayList: ArrayList<Product>? = null

    private var tmpprodArrayList= mutableListOf<Product>()
    private var prodArrayList: ArrayList<Product>? = null

    private var restaurantList: ArrayList<Restaurant>? = null
    private var restaurant : Restaurant? = null
    private lateinit var user : FirebaseAuth
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
        restaurant = restaurantFromId(restaurantID.toString())

        user = FirebaseAuth.getInstance()
        user.currentUser?.email

        if (restaurant != null) {
            binding.btnModificaRistorante.isVisible = restaurant?.proprietarioR == user.currentUser?.email
            binding.btnEliminaRistorante.isVisible = restaurant?.proprietarioR == user.currentUser?.email
        }

        if (restaurant != null) {
            val imageName = restaurant?.imageR
            val storageRef = FirebaseStorage.getInstance().reference.child("$imageName")
            val localfile = File.createTempFile("tempImage", "jpg")
            storageRef.getFile(localfile).addOnSuccessListener {
                var bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                bitmap = getCircularBitmap(bitmap)
                binding.imgRistoranteDetail.setImageBitmap(bitmap)
            }.addOnFailureListener {
                Toast.makeText(context, "Caricamento immagine fallito", Toast.LENGTH_SHORT).show()
            }

            binding.nomeRistoranteDetail.text = restaurant?.nomeR
            binding.descrizioneDetail.text = restaurant?.descrizioneR
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

        val bundle = Bundle()
        getBevanda(restaurantID.toString(),
            object: FireBaseCallbackProdotto{
                override fun onResponse(responseP: ResponseProdotto) {
                    bevandeArrayList = responseP.prodotto
                }
            },context)
        getAntipasto(restaurantID.toString(),
            object: FireBaseCallbackProdotto{
                override fun onResponse(responseP: ResponseProdotto) {
                    antipastiArrayList = responseP.prodotto
                }
            },context)
        getPrimo(restaurantID.toString(),
            object: FireBaseCallbackProdotto{
                override fun onResponse(responseP: ResponseProdotto) {
                    primiArrayList = responseP.prodotto
                }
            },context)
        getSecondo(restaurantID.toString(),
            object: FireBaseCallbackProdotto{
                override fun onResponse(responseP: ResponseProdotto) {
                    secondiArrayList = responseP.prodotto
                }
            },context)
        getContorno(restaurantID.toString(),
            object: FireBaseCallbackProdotto{
                override fun onResponse(responseP: ResponseProdotto) {
                    contornoArrayList = responseP.prodotto
                }
            },context)
        getDolce(restaurantID.toString(),
            object: FireBaseCallbackProdotto{
                override fun onResponse(responseP: ResponseProdotto) {
                    dolceArrayList = responseP.prodotto
                }
            },context)


        binding.visualizzaMenu.setOnClickListener {
            //PASSAGGIO DATI RISTORANTI -->(CONTIENE ANCHE TUTTI I MENU)

            tmpprodArrayList.addAll(bevandeArrayList!!.toMutableList())
            tmpprodArrayList.addAll(antipastiArrayList!!.toMutableList())
            tmpprodArrayList.addAll(primiArrayList!!.toMutableList())
            tmpprodArrayList.addAll(secondiArrayList!!.toMutableList())
            tmpprodArrayList.addAll(contornoArrayList!!.toMutableList())
            tmpprodArrayList.addAll(dolceArrayList!!.toMutableList())

            prodArrayList=tmpprodArrayList as ArrayList<Product>
            bundle.putParcelableArrayList("prodotti", prodArrayList )
            bundle.putString("restID", restaurantID.toString())
            bundle.putParcelableArrayList("restArrayList", restaurantList)

            view?.findNavController()?.navigate(R.id.DetailToMenu,bundle)
        }

        binding.eliminaRistorante.setOnClickListener{
            val builder = AlertDialog.Builder(activity)
            val reference = firebaseDatabase.getReference("Ristoranti")
            val idR = restaurant?.idR
            builder.setTitle("Conferma l'eliminazione del ristorante")
            builder.setMessage("Sei sicuro di voler eliminare il ristorante?")
            builder.setPositiveButton("Sì") { dialog, _ ->
                reference.child("$idR").removeValue()
                view?.findNavController()?.navigate(R.id.Ristoranti_R)

            }
            builder.setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
            }
            builder.show()

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

    private fun getCircularBitmap(srcBitmap: Bitmap?): Bitmap {
        val squareBitmapWidth = Integer.min(srcBitmap!!.width, srcBitmap.height)
        // Initialize a new instance of Bitmap
        // Initialize a new instance of Bitmap
        val dstBitmap = Bitmap.createBitmap(
            squareBitmapWidth,  // Width
            squareBitmapWidth,  // Height
            Bitmap.Config.ARGB_8888 // Config
        )
        val canvas = Canvas(dstBitmap)
        // Initialize a new Paint instance
        // Initialize a new Paint instance
        val paint = Paint()
        paint.isAntiAlias = true
        val rect = Rect(0, 0, squareBitmapWidth, squareBitmapWidth)
        val rectF = RectF(rect)
        canvas.drawOval(rectF, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        // Calculate the left and top of copied bitmap
        // Calculate the left and top of copied bitmap
        val left = ((squareBitmapWidth - srcBitmap.width) / 2).toFloat()
        val top = ((squareBitmapWidth - srcBitmap.height) / 2).toFloat()
        canvas.drawBitmap(srcBitmap, left, top, paint)
        // Free the native object associated with this bitmap.
        // Free the native object associated with this bitmap.
        srcBitmap.recycle()
        // Return the circular bitmap
        // Return the circular bitmap
        return dstBitmap
    }



}