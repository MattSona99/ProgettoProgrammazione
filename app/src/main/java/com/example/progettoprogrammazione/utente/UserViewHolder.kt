package com.example.progettoprogrammazione.utente

import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.FragmentProfiloBinding
import com.example.progettoprogrammazione.databinding.FragmentProfiloCardBinding
import com.example.progettoprogrammazione.models.User

class UserViewHolder(
    private val userBinding: FragmentProfiloCardBinding,
    private val clickListener: UserClickListener
)
    :RecyclerView.ViewHolder(userBinding.root) {
        fun bindUser(user: User){
            userBinding.nomeprofilo.setHint(user.Nome)
            userBinding.cognomeprofilo.setHint(user.Cognome)
            userBinding.passwordprofilo.setHint(user.Password)
            userBinding.salva.setOnClickListener{clickListener.onClick(user)
            }
        }
}