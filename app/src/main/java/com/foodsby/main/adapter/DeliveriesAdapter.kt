package com.foodsby.main.adapter

import android.content.Context
import android.net.Uri
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.foodsby.R
import com.foodsby.model.network.Delivery
import kotlinx.android.synthetic.main.delivery_row_item.view.*



class DeliveriesAdapter(private var deliveries: List<Delivery>, private val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    private var expandedPosition = -1
    private var previousExpandedPosition  = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.delivery_row_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dropoff = deliveries[position]

        holder.deliveryItemRestaurantName.text = dropoff.restaurantName
        holder.deliveryItemOrderByTime.text = dropoff.cutoff
        holder.deliveryItemDeliveryTime.text = dropoff.dropoff
        holder.deliveryItemRestaurantImage.setImageURI(Uri.parse(dropoff.logoUrl))

        holder.deliveryItemStatusImage.setImageDrawable(ContextCompat.getDrawable(context, getImageFromDropOff(dropoff)))

        val isExpanded = position == expandedPosition
        holder.deliveryItemDeliveryStatus.visibility = if (isExpanded) View.VISIBLE else View.GONE

        if(canBeExpanded(dropoff)) {
            holder.deliveryItemDeliveryStatus.text = getStatusFromDropOff(dropoff)
            holder.deliveryItemDeliveryStatus.setBackgroundColor(ContextCompat.getColor(context, getBackgroundColorFromDropOff(dropoff)))

            holder.itemView.isActivated = isExpanded

            if (isExpanded) {
                previousExpandedPosition = position
            }

            holder.itemView.setOnClickListener {
                expandedPosition = if (isExpanded) -1 else position
                notifyItemChanged(previousExpandedPosition)
                notifyItemChanged(position)
            }
        } else {
            holder.itemView.setOnClickListener {
                Snackbar.make(it, "Order placed!", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun canBeExpanded(dropOff: Delivery): Boolean {
        return when {
            dropOff.soldOut == true -> true
            dropOff.isOrderPlaced == true -> true
            dropOff.isPastCutoff == true -> true
            dropOff.sellingOut == true -> true
            else -> {
                false
            }
        }
    }

    private fun getBackgroundColorFromDropOff(dropOff: Delivery): Int {
        return when {
            dropOff.soldOut == true -> R.color.material_red_500
            dropOff.isOrderPlaced == true -> R.color.material_green_500
            dropOff.isPastCutoff == true -> R.color.material_red_500
            dropOff.sellingOut == true -> R.color.material_yellow_500
            else -> {
                android.R.color.white
            }
        }
    }


    private fun getImageFromDropOff(dropOff: Delivery): Int {
        return when {
            dropOff.soldOut == true -> R.drawable.ic_sentiment_dissatisfied_red_500_24dp
            dropOff.isOrderPlaced == true -> R.drawable.ic_check_circle_green_500_24dp
            dropOff.isPastCutoff == true -> R.drawable.ic_timer_off_red_500_24dp
            dropOff.sellingOut == true -> R.drawable.ic_warning_yellow_500_24dp
            else -> {
                android.R.drawable.screen_background_dark_transparent
            }
        }
    }


    private fun getStatusFromDropOff(dropoff: Delivery): String {
        return when {
            dropoff.isPastCutoff == true -> "Cut-Off Time Passed"
            dropoff.isOrderPlaced == true -> "Order is placed!"
            dropoff.soldOut == true ->"Order is sold out!"
            dropoff.sellingOut == true -> "Order is selling out, hurry!"
            else -> "Place your order!"
        }
    }

    // Gets the number of deliveries in the list
    override fun getItemCount(): Int {
        return deliveries.size
    }

    fun update(newDeliveries: List<Delivery>) {
        expandedPosition = -1
        previousExpandedPosition = -1

        val diffCallback = DiffUtilCallback(newDeliveries, deliveries)
        val diffResult = DiffUtil.calculateDiff(diffCallback, true)

        deliveries = ArrayList()
        (deliveries as ArrayList<Delivery>).addAll(newDeliveries)
        diffResult.dispatchUpdatesTo(this)
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val deliveryItemRestaurantName = view.deliveryItemRestaurantName as TextView
    val deliveryItemRestaurantImage = view.deliveryItemRestaurantImage as ImageView
    val deliveryItemStatusImage = view.deliveryItemStatusImage as ImageView
    val deliveryItemOrderByTime = view.deliveryItemOrderByTime as TextView
    val deliveryItemDeliveryTime = view.deliveryItemDeliveryTime as TextView
    val deliveryItemDeliveryStatus = view.deliveryItemDeliveryStatus as TextView
}