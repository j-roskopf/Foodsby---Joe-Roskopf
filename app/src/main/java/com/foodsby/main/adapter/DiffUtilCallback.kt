package com.foodsby.main.adapter

import android.support.v7.util.DiffUtil
import com.foodsby.model.network.Delivery


class DiffUtilCallback(private var newDropOff: List<Delivery>, private var oldDropOff: List<Delivery>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldDropOff.size
    }

    override fun getNewListSize(): Int {
        return newDropOff.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldDropOff[oldItemPosition].storeId === newDropOff[newItemPosition].storeId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldDropOff[oldItemPosition].storeId === newDropOff[newItemPosition].storeId
    }
}