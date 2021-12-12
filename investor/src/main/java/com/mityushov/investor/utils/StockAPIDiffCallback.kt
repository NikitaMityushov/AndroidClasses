package com.mityushov.investor.utils

import androidx.recyclerview.widget.DiffUtil
import com.mityushov.investor.models.CacheStockPurchase

class StockAPIDiffCallback: DiffUtil.ItemCallback<CacheStockPurchase>() {
    override fun areItemsTheSame(oldItem: CacheStockPurchase, newItem: CacheStockPurchase): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CacheStockPurchase, newItem: CacheStockPurchase): Boolean {
        return oldItem == newItem
    }
}