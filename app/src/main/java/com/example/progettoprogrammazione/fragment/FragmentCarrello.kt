package com.example.progettoprogrammazione.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.databinding.ShoppingCartBinding

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
}