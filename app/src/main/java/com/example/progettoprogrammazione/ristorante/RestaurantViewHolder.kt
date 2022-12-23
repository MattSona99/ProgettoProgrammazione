package com.example.progettoprogrammazione.ristorante

import android.content.Context
import android.graphics.*
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.RestaurantCardBinding
import com.example.progettoprogrammazione.models.Restaurant
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.lang.Integer.min

class RestaurantViewHolder(
    private val restaurantBinding: RestaurantCardBinding,
    private val clickListener: RestaurantClickListener
) : RecyclerView.ViewHolder(restaurantBinding.root) {

    fun bindRestaurants(restaurant: Restaurant) {
        val imageName = restaurant.imageR
        val storageRef = FirebaseStorage.getInstance().reference.child("$imageName")
        val localfile = File.createTempFile("tempImage", "jpg")
        storageRef.getFile(localfile).addOnSuccessListener {
            var bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            bitmap = getCircularBitmap(bitmap)
            restaurantBinding.copertina.setImageBitmap(bitmap)
        }

        restaurantBinding.copertina.setImageResource(
            getImageId(
                restaurantBinding.root.context,
                restaurant.imageR!!
            )
        )
        restaurantBinding.nomeRistorante.text = restaurant.nomeR
        restaurantBinding.rating.text = restaurant.ratingR
        restaurantBinding.descrizione.text = restaurant.descrizioneR
        restaurantBinding.tipocibo.text = restaurant.tipoCiboR

        restaurantBinding.btncard.setOnClickListener { clickListener.onClickResturant(restaurant) }
    }

    private fun getImageId(context: Context, imageName: String): Int {
        return context.resources
            .getIdentifier("drawable/$imageName", null, context.packageName)
    }

    private fun getCircularBitmap(srcBitmap: Bitmap?): Bitmap {
        val squareBitmapWidth = min(srcBitmap!!.width, srcBitmap.height)
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