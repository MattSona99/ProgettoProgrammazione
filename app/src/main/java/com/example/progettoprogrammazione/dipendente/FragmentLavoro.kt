package com.example.progettoprogrammazione.dipendente

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.util.isNotEmpty
import androidx.fragment.app.Fragment
import com.example.progettoprogrammazione.databinding.FragmentLavoroBinding
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector


class FragmentLavoro : Fragment() {

    private lateinit var binding: FragmentLavoroBinding

    private val requestCodeCameraPermission = 1001
    private lateinit var cameraSource: CameraSource
    private lateinit var detector: BarcodeDetector

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLavoroBinding.inflate(layoutInflater)
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            askForCameraPermission()
        } else {
            setUpControls()
        }


        return binding.root
    }

    private fun askForCameraPermission() {
        requireParentFragment().requestPermissions(
            arrayOf(Manifest.permission.CAMERA),
            requestCodeCameraPermission
        )
    }

    private fun setUpControls() {
        detector = BarcodeDetector.Builder(requireContext()).build()
        cameraSource =
            CameraSource.Builder(requireContext(), detector).setAutoFocusEnabled(true).build()
        binding.cameraPreview.holder.addCallback(surfaceCallback)
        detector.setProcessor(processor)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == requestCodeCameraPermission && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setUpControls()
            } else {
                Toast.makeText(context, "Permesso negato.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val surfaceCallback = object  : SurfaceHolder.Callback {
        override fun surfaceCreated(p0: SurfaceHolder) {

        }

        override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
            cameraSource.stop()
        }

        override fun surfaceDestroyed(p0: SurfaceHolder) {
            try {
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.CAMERA
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    return
                }
                cameraSource.start(p0)
            } catch (exception: Exception) {
                Toast.makeText(context, "Qualcosa è andato storto.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val processor = object : Detector.Processor<Barcode>{
        override fun release() {

        }

        override fun receiveDetections(p0: Detector.Detections<Barcode>) {
            if( p0 != null && p0.detectedItems.isNotEmpty()){
                val qrCodes : SparseArray<Barcode> = p0.detectedItems
                val code = qrCodes.valueAt(0)
                binding.scanResult.text = code.displayValue
            } else {
                binding.scanResult.text = ""
            }
        }

    }


}