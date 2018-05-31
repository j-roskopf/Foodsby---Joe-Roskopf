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
        val delivery = deliveries[position]

        holder.deliveryItemRestaurantName.text = delivery.restaurantName
        holder.deliveryItemOrderByTime.text = delivery.cutoff
        holder.deliveryItemDeliveryTime.text = delivery.dropoff
        holder.deliveryItemRestaurantImage.setImageURI(Uri.parse(delivery.logoUrl))

        holder.deliveryItemStatusImage.setImageDrawable(ContextCompat.getDrawable(context, getImageFromDelivery(delivery)))

        setupExpansionLogic(position, holder, delivery)
    }

    /**
     * Handles the logic to expand a cell when clicked or handle the onClick of the cell in another way
     *
     * @param position - current position in our adapter
     * @param holder - the current [ViewHolder] expanded for this position
     * @param delivery - the delivery to display
     */
    private fun setupExpansionLogic(position: Int, holder: ViewHolder, delivery: Delivery) {

        val isExpanded = position == expandedPosition
        holder.deliveryItemDeliveryStatus.visibility = if (isExpanded) View.VISIBLE else View.GONE

        if(canBeExpanded(delivery)) {
            holder.deliveryItemDeliveryStatus.text = context.getString(getStatusFromDelivery(delivery))
            holder.deliveryItemDeliveryStatus.setBackgroundColor(ContextCompat.getColor(context, getBackgroundColorFromDelivery(delivery)))

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
                Snackbar.make(it, context.getString(R.string.delivery_item_order_placed), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Not all items can be expanded. If it is sold out, selling out, past cut off, or already placed,
     * the row item can be expanded, otherwise it cannot
     *
     * @param delivery - the delivery to check
     *
     * @return Boolean representing whether or not the row item can be expanded
     */
    fun canBeExpanded(delivery: Delivery): Boolean {
        return when {
            delivery.soldOut == true -> true
            delivery.isOrderPlaced == true -> true
            delivery.isPastCutoff == true -> true
            delivery.sellingOut == true -> true
            else -> {
                false
            }
        }
    }

    /**
     * Get the background color for our status text based on the delivery
     *
     * @param delivery - the delivery to check
     *
     * @return Int color resource
     */
    fun getBackgroundColorFromDelivery(delivery: Delivery): Int {
        return when {
            delivery.soldOut == true -> R.color.material_red_500
            delivery.isOrderPlaced == true -> R.color.material_green_500
            delivery.isPastCutoff == true -> R.color.material_red_500
            delivery.sellingOut == true -> R.color.material_yellow_500
            else -> {
                android.R.color.white
            }
        }
    }


    /**
     * Gets image to display in the row item based on the [Delivery]
     *
     * @param delivery - the delivery to check
     *
     * @return - Drawable resource based on the passed in delievery
     */
    fun getImageFromDelivery(delivery: Delivery): Int {
        return when {
            delivery.soldOut == true -> R.drawable.ic_sentiment_dissatisfied_red_500_24dp
            delivery.isOrderPlaced == true -> R.drawable.ic_check_circle_green_500_24dp
            delivery.isPastCutoff == true -> R.drawable.ic_timer_off_red_500_24dp
            delivery.sellingOut == true -> R.drawable.ic_warning_yellow_500_24dp
            else -> {
                android.R.drawable.screen_background_dark_transparent
            }
        }
    }


    /**
     * Based on the [Delivery], return an appropriate String resource
     *
     * @param delivery - the [Delivery] in question
     *
     * @return - Int representing the String resource to display in the row item
     */
    fun getStatusFromDelivery(delivery: Delivery): Int {
        return when {
            delivery.isPastCutoff == true -> R.string.delivery_item_past_cut_off
            delivery.isOrderPlaced == true -> R.string.delivery_item_order_placed
            delivery.soldOut == true -> R.string.delivery_item_sold_out
            delivery.sellingOut == true -> R.string.delivery_item_selling_out
            else -> -1
        }
    }

    /**
     * Gets the number of deliveries in the list
     *
     * @return - Int describing the number of deliveries in our list
     */
    override fun getItemCount(): Int {
        return deliveries.size
    }

    /**
     * Updates the list via DiffUtil.
     *
     * This method is also responsible for resetting our placeholder for the expanded position
     * and the previously expanded position
     *
     * @param newDeliveries - the new deliveries to update the list with
     */
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

/**
 * View holder class describing our [Delivery] row item
 */
class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val deliveryItemRestaurantName = view.deliveryItemRestaurantName as TextView
    val deliveryItemRestaurantImage = view.deliveryItemRestaurantImage as ImageView
    val deliveryItemStatusImage = view.deliveryItemStatusImage as ImageView
    val deliveryItemOrderByTime = view.deliveryItemOrderByTime as TextView
    val deliveryItemDeliveryTime = view.deliveryItemDeliveryTime as TextView
    val deliveryItemDeliveryStatus = view.deliveryItemDeliveryStatus as TextView
}