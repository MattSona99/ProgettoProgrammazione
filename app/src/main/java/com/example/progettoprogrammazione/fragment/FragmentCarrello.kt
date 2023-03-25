package com.example.progettoprogrammazione.fragment

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.ShoppingCartBinding
import com.example.progettoprogrammazione.firebase.FireBaseCallbackShoppingCart
import com.example.progettoprogrammazione.models.Cart
import com.example.progettoprogrammazione.models.Product
import com.example.progettoprogrammazione.prodotti.ProductClickListener
import com.example.progettoprogrammazione.shoppingcart.ShoppingCartAdapter
import com.example.progettoprogrammazione.utils.ProductUtils
import com.example.progettoprogrammazione.utils.ResponseShoppingCart
import com.example.progettoprogrammazione.utils.ShoppingCartUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter

class FragmentCarrello(
    override var firebaseAuth: FirebaseAuth,
    override var firebaseDatabase: FirebaseDatabase
) : Fragment(),ShoppingCartUtils,ProductClickListener,ProductUtils {

    private lateinit var binding: ShoppingCartBinding

    private lateinit var cart: HashMap<String, Cart>
    private lateinit var adapter: ShoppingCartAdapter
    private var prodotti = arrayListOf<Product>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ShoppingCartBinding.inflate(layoutInflater)
        setHasOptionsMenu(true);

        getShoppingCartData(FirebaseAuth.getInstance().uid, object : FireBaseCallbackShoppingCart {
            override fun onResponse(responseC: ResponseShoppingCart) {
                cart = responseC.carrello

            }
        }, context)

        val layoutManager = GridLayoutManager(context, 2)
        binding.recylerOrder.layoutManager = layoutManager
        adapter = ShoppingCartAdapter(cart, this@FragmentCarrello)
        showData(cart)
        binding.recylerOrder.adapter = adapter
        binding.recylerOrder.setHasFixedSize(true)
        adapter.notifyDataSetChanged()

        return binding.root
    }

    private fun showData(cart: HashMap<String,Cart>) {
        adapter.setData(this.cart)
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        val menuItem = menu.findItem(R.id.ic_cart)
        if (menuItem != null) {
            menuItem.isVisible = false
        }
        super.onPrepareOptionsMenu(menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.constraintQR.setOnClickListener {

            val qrcodecontent = "ciao"
            val multiFormatWriter = MultiFormatWriter()
            val bitMatrix = multiFormatWriter.encode(qrcodecontent, BarcodeFormat.QR_CODE, 200, 200)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
                }
            }
            val bundle = Bundle()
            bundle.putParcelable("qrcode", bitmap)
            view.findNavController().navigate(R.id.CarrelloToQR_R, bundle)
        }
    }

    override fun onClickProduct(prodotto: Product) {
        val bundle = Bundle()
        bundle.putString("prodID", prodotto.idP.toString())
        bundle.putParcelableArrayList("prodArrayList", prodotti)
        view?.findNavController()?.navigate(R.id.productDetail, bundle)
    }

}
