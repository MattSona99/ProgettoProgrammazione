package com.example.progettoprogrammazione.utente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.FragmentUpgradeBinding
import com.example.progettoprogrammazione.models.User
import com.example.progettoprogrammazione.ristoratore.FragmentCreaRist

class FragmentUpgrade : Fragment() {

    private lateinit var user: User

    private lateinit var binding: FragmentUpgradeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUpgradeBinding.inflate(layoutInflater)

        val args = this.arguments
        user = args?.getParcelable<User>("user") as User

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
