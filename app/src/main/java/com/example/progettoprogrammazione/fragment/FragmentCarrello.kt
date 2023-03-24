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
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.ShoppingCartBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter

class FragmentCarrello : Fragment() {

    private lateinit var binding: ShoppingCartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ShoppingCartBinding.inflate(layoutInflater)
        setHasOptionsMenu(true);
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

        binding.qrcode.setOnClickListener {

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
}