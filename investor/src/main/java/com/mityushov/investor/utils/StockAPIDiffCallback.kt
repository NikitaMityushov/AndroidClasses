package com.mityushov.investor.utils

import androidx.recyclerview.widget.DiffUtil
import com.mityushov.investor.models.StockAPI

class StockAPIDiffCallback: DiffUtil.ItemCallback<StockAPI>() {
    override fun areItemsTheSame(oldItem: StockAPI, newItem: StockAPI): Boolean {
        return oldItem.getId() == newItem.getId()
    }

    override fun areContentsTheSame(oldItem: StockAPI, newItem: StockAPI): Boolean {
        return oldItem.getStockPurchase() == newItem.getStockPurchase()
    }
}