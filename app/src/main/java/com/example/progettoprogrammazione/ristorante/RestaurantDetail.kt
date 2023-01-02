package com.example.progettoprogrammazione.ristorante

import android.graphics.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.FragmentRestaurantDetailBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackProdotto
import com.example.progettoprogrammazione.models.Product
import com.example.progettoprogrammazione.models.Restaurant
import com.example.progettoprogrammazione.models.User
import com.example.progettoprogrammazione.prodotti.ProductClickListener
import com.example.progettoprogrammazione.utils.ProductUtils
import com.example.progettoprogrammazione.utils.ResponseProdotto
import com.example.progettoprogrammazione.viewmodels.ProductViewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class RestaurantDetail : Fragment(), ProductClickListener, ProductUtils {

    private lateinit var binding: FragmentRestaurantDetailBinding

    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()

    private lateinit var productDataViewModel: ProductViewModel

    private var prodArrayList: ArrayList<Product>? = null
    private var restaurantList: ArrayList<Restaurant>? = null

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

        val restaurant = restaurantFromId(restaurantID.toString())

        if (restaurant != null) {
            val imageName = restaurant.imageR
            val storageRef = FirebaseStorage.getInstance().reference.child("$imageName")
            val localfile = File.createTempFile("tempImage", "jpg")
            storageRef.getFile(localfile).addOnSuccessListener {
                var bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                bitmap = getCircularBitmap(bitmap)
                binding.imgRistoranteDetail.setImageBitmap(bitmap)
            }.addOnFailureListener {
                Toast.makeText(context, "Caricamento immagine fallito", Toast.LENGTH_SHORT).show()
            }

            binding.nomeRistoranteDetail.text = restaurant.nomeR
            binding.descrizioneDetail.text = restaurant.descrizioneR
            binding.numeroDetail.text = restaurant.telefonoR
            binding.indirizzodetail.text = restaurant.indirizzoR

        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = this.arguments
        val restaurantID = args?.get("restID")
        restaurantList = args?.getParcelableArrayList("restArrayList")

        val bundle = Bundle()
        getMenu(restaurantID.toString(),
            object: FireBaseCallbackProdotto{
                override fun onResponse(responseP: ResponseProdotto) {
                    prodArrayList= responseP.prodotto
                }
            },context)

        binding.visualizzaMenu.setOnClickListener {
            //PASSAGGIO DATI RISTORANTI -->(CONTIENE ANCHE TUTTI I MENU)

            bundle.putParcelableArrayList("prodotti", prodArrayList )
            bundle.putString("restID", restaurantID.toString())
            bundle.putParcelableArrayList("restArrayList", restaurantList)

/*
            productDataViewModel = ViewModelProvider(this)[productDataViewModel::class.java]
            productDataViewModel.arrayListaprodottiLiveData.postValue()
*/
            view?.findNavController()?.navigate(R.id.DetailToMenu,bundle)
        }

    }

    override fun onClickProduct(prodotto: Product) {

        val bundle1 = Bundle()
        bundle1.putString("prodID", prodotto.idP.toString())
        bundle1.putParcelableArrayList("prodArrayList", prodArrayList)

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