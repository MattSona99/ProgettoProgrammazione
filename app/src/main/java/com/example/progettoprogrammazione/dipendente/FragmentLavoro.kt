package com.example.progettoprogrammazione.dipendente

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.util.isNotEmpty
import androidx.fragment.app.Fragment
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.FragmentLavoroBinding
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class FragmentLavoro : Fragment() {

    private lateinit var binding: FragmentLavoroBinding

    lateinit var btnBarcode: Button
    lateinit var textView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLavoroBinding.inflate(layoutInflater)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnBarcode = view.findViewById(R.id.btn_qr)
        textView = view.findViewById(R.id.scan_result)

        btnBarcode.setOnClickListener {
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
                textView.text = "Scan fallito"
            } else {
          //      try {
                    val o = JSONArray(result.contents)

                for (i in 0 until o.length()) {
                    val jsonObject = o.getJSONObject(i)
                    binding.scanResult.text = jsonObject.getString("pname")
                    binding.scanResult2.text = jsonObject.getInt("quantity").toString()

                }

            /*
                } catch (e: JSONException) {
                    e.printStackTrace()

                    // Data not in the expected format. So, whole object as toast message.
                    Toast.makeText(activity, result.contents, Toast.LENGTH_LONG).show()
                }

 */
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}