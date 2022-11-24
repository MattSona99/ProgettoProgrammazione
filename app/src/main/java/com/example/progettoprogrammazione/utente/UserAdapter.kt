package com.example.progettoprogrammazione.utente

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.R
import com.example.progettoprogrammazione.models.User
import com.example.progettoprogrammazione.models.userList
import com.example.progettoprogrammazione.ristorante.RestaurantAdapter

class UserAdapter(
    private val user: ArrayList<User>,
    //   private val clickListener: UserClickListener
) :
    RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_profilo_card,
            parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = user[position]
        holder.nome_u.text = currentItem.Nome
        holder.cognome_u.text = currentItem.Cognome
        holder.password_u.text = currentItem.Password
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome_u: TextView = itemView.findViewById(R.id.nomeprofilo)
        val cognome_u: TextView = itemView.findViewById(R.id.cognomeprofilo)
        val password_u: TextView = itemView.findViewById(R.id.passwordprofilo)
    }

}