package com.example.progettoprogrammazione.dipendente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.progettoprogrammazione.databinding.FragmentDipendenteDetailBinding
import com.example.progettoprogrammazione.models.Dipendente

class DipendenteDetail : Fragment(){

    private lateinit var binding: FragmentDipendenteDetailBinding

    private var dipendenteList : ArrayList<Dipendente>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentDipendenteDetailBinding.inflate(layoutInflater)

        /*
        PRENDO DATI RISTORANTE DAL BUNDLE

        val args = this.arguments
        val dipendenteID = args?.get("dipID")
        dipendenteList = args?.getParcelableArrayList<Dipendente>("dipArrayList")


        val dipendente=dipendenteFromId(dipendenteID.toString().toInt())

        if(dipendente != null)
        {
            /*
            binding.imgRistoranteDetail.setImageResource( (dipendente.imageR)!!.toInt())
            binding.nomeRistoranteDetail.text = dipendente.nomeR
            binding.descrizioneDetail.text = dipendente.descrizioneR

             */

        }
        return binding.root
    }

    private fun dipendenteFromId(dipendenteID: Int?): Dipendente? {
        for (dipendente in dipendenteList!!) {
            if(dipendente.id == dipendenteID)
                return dipendente
        }
        */

        return null
    }

}