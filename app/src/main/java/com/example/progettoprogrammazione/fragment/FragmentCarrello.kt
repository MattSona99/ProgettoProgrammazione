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
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.carrello.CartAdapter
import com.example.progettoprogrammazione.databinding.FragmentCarrelloBinding
import com.example.progettoprogrammazione.models.CartProduct
import com.example.progettoprogrammazione.models.Product
import com.example.progettoprogrammazione.models.User
import com.example.progettoprogrammazione.prodotti.ProductClickListener
import com.example.progettoprogrammazione.utils.ProductUtils
import com.example.progettoprogrammazione.utils.ShoppingCartUtils
import com.example.progettoprogrammazione.viewmodels.CartViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter

class FragmentCarrello : Fragment(), ShoppingCartUtils, ProductClickListener, ProductUtils {

    override var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    override var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var binding: FragmentCarrelloBinding
    private lateinit var user: User

    private val cartViewModel : CartViewModel by navGraphViewModels(R.id.nav_restaurateur)


    private var cartProduct = arrayListOf<CartProduct>()
    private var prodotti = arrayListOf<Product>()
    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCarrelloBinding.inflate(layoutInflater)
        setHasOptionsMenu(true)

        val args = this.arguments
        user = args?.getParcelable<User>("user") as User

        return binding.root
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

        cartViewModel.getcartItems().observe(viewLifecycleOwner) { cartItems ->
            var totale: Float? = 0f
            for (c in cartItems) {
                totale = totale?.plus(c.totPrice!!)
            }
            binding.totaleCarrello.text = totale.toString() + " €"
            val layoutManager = GridLayoutManager(context, 2)
            binding.recylerOrder.layoutManager = layoutManager
            adapter = CartAdapter(cartItems, requireContext())
            adapter.setData(cartItems)
            binding.recylerOrder.adapter = adapter
            binding.recylerOrder.setHasFixedSize(true)
            adapter.notifyDataSetChanged()
        }

        binding.constraintQR.setOnClickListener {

            val qrcodecontent = this.cartProduct.toString()
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
            when (user.Livello) {
                "1" -> view.findNavController().navigate(R.id.CarrelloToQR_U, bundle)
                "2" -> view.findNavController().navigate(R.id.CarrelloToQR_D, bundle)
                "3" -> view.findNavController().navigate(R.id.CarrelloToQR_R, bundle)
            }

        }
    }

    override fun onClickProduct(prodotto: Product) {
        val bundle = Bundle()
        bundle.putString("prodID", prodotto.idP.toString())
        bundle.putParcelableArrayList("prodArrayList", prodotti)
        view?.findNavController()?.navigate(R.id.productDetail, bundle)
    }
}
