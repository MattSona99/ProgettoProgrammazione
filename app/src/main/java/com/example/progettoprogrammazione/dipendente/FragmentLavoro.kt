package com.example.progettoprogrammazione.dipendente

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.progettoprogrammazione.activity.EmployeeActivity
import com.example.progettoprogrammazione.databinding.ActivityIntroBinding
import com.example.progettoprogrammazione.databinding.FragmentLavoroBinding
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.CaptureActivity


class FragmentLavoro : Fragment() {

    private lateinit var binding: FragmentLavoroBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLavoroBinding.inflate(layoutInflater)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.scannerizza.setOnClickListener {
            val scanner = IntentIntegrator.forSupportFragment(requireParentFragment())
            scanner.setOrientationLocked(false)
            scanner.setPrompt("Scannerizza QRCOde")
            scanner.setBeepEnabled(false)
            scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            scanner.initiateScan()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                if (result.contents == null) {
                    Toast.makeText(context, "Cancelled.", Toast.LENGTH_SHORT).show()
                } else {
                    binding.scanResult.text = result.contents
                }
            }
        }
    }
}