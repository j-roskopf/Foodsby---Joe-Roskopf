package com.foodsby.main.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.foodsby.R
import com.foodsby.model.network.Delivery
import kotlinx.android.synthetic.main.delivery_row_item.view.*

class DeliveriesAdapter(private val deliveries: List<Delivery>, private val context: Context) : RecyclerView.Adapter<ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.delivery_row_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.restaurantName.text = deliveries[position].restaurantName
/*        holder.tacoHeaderText.text = MainViewModel.getHeadingFromTaco(tacos[position].toTacoResponse())
        holder.tacoHeaderCloseButton.setOnClickListener {
            onItemClickListener.onItemCloseClicked(tacos[position])
        }
        holder.tacoRowItemBaseLayout.setOnClickListener {
            onItemClickListener.onItemClick(tacos[position].toTacoResponse())
        }*/
    }

    // Gets the number of tacos in the list
    override fun getItemCount(): Int {
        return deliveries.size
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val restaurantName = view.deliveryItemRestaurantName as TextView
}