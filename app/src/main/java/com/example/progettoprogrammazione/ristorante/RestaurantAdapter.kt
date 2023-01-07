package com.example.progettoprogrammazione.ristorante

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.progettoprogrammazione.databinding.RestaurantCardBinding
import com.example.progettoprogrammazione.models.Restaurant

class RestaurantAdapter(
    private var restaurant: ArrayList<Restaurant>,
    private val clickListener: RestaurantClickListener
) :
    RecyclerView.Adapter<RestaurantViewHolder>(), Filterable {

    private var restaurantFiltered: ArrayList<Restaurant> = arrayListOf()

    fun setData(restaurant: ArrayList<Restaurant>) {
        this.restaurant = restaurant
        this.restaurantFiltered = restaurant
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        val binding = RestaurantCardBinding.inflate(itemView, parent, false)
        return RestaurantViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bindRestaurants(restaurant[position])
    }

    override fun getItemCount(): Int {
        return restaurant.size
    }

    override fun getFilter(): Filter {
        val filter = object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (p0 == null || p0.isEmpty()) {
                    filterResults.values = restaurantFiltered
                    filterResults.count = restaurantFiltered.size
                } else {
                    val searchChar = p0.toString().lowercase()
                    val filteredResults = ArrayList<Restaurant>()

                    for (ristorante in restaurantFiltered) {
                        if (ristorante.nomeR!!.lowercase().contains(searchChar)) {
                            filteredResults.add(ristorante)
                        }
                    }
                    filterResults.values = filteredResults
                    filterResults.count = filteredResults.size
                }
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                restaurant = p1!!.values as ArrayList<Restaurant>
                notifyDataSetChanged()
            }
        }
        return filter
    }

    fun customFilter(): Filter {
        val filter = object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (p0 == null || p0.isEmpty()) {
                    filterResults.values = restaurantFiltered
                    filterResults.count = restaurantFiltered.size
                } else {
                    val searchChar = p0.toString().lowercase()
                    val filteredResults = ArrayList<Restaurant>()

                    for (ristorante in restaurantFiltered) {
                        if(searchChar=="vegan"){
                            if(ristorante.veganR) {
                                filteredResults.add(ristorante)
                            }
                        }
                        if (ristorante.tipoCiboR!!.lowercase().contains(searchChar)) {
                            filteredResults.add(ristorante)
                        }
                    }
                    filterResults.values = filteredResults
                    filterResults.count = filteredResults.size
                }
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                restaurant = p1!!.values as ArrayList<Restaurant>
                notifyDataSetChanged()
            }

        }
        return filter
    }
}
