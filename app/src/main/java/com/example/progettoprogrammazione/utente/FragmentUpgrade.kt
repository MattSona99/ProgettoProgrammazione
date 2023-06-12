package com.example.progettoprogrammazione.utente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.FragmentUUpgradeBinding
import com.example.progettoprogrammazione.models.User
import com.example.progettoprogrammazione.ristoratore.FragmentCreaRist

// Made by Alessandro Pieragostini, Matteo Sonaglioni & Stefano Marcucci
/* Questo fragment permette all'utente di effettuare, qualora disponibile, l'upgrade dell'account;
 L'upgrade dell'account può essere effettuato da utente a ristoratore o dipendente */

class FragmentUpgrade : Fragment() {

    private lateinit var user: User

    private lateinit var binding: FragmentUUpgradeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUUpgradeBinding.inflate(layoutInflater)

        val args = this.arguments
        user = args?.getParcelable<User>("user") as User

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Cliccando sul bottone, il fragment temporaneo verrà cambiato con quello per
        effettuare l'upgrade a proprietario, iniziandolo creando il primo ristorante */
        binding.btnProprietario.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("user", user)
            val myfrag = FragmentCreaRist()
            myfrag.arguments = bundle
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(R.anim.slide_in, R.anim.fade_out)
            fragmentTransaction.replace(R.id.container_upgrade, myfrag)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        }

        /* Cliccando sul bottone, il fragment temporaneo verrà cambiato con quello per
        effettuare l'upgrade a dipendente, compilando determinati campi */
        binding.btnDipendente.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("user", user)
            val myfrag = FragmentUpgradeDipendente()
            myfrag.arguments = bundle
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(R.anim.slide_in, R.anim.fade_out)
            fragmentTransaction.replace(R.id.container_upgrade, myfrag)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

}
