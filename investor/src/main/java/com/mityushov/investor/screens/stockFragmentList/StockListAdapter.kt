package com.mityushov.investor.screens.stockFragmentList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mityushov.investor.R
import com.mityushov.investor.databinding.StockItemBinding
import com.mityushov.investor.interfaces.Navigator
import com.mityushov.investor.models.StockAPI
import com.mityushov.investor.utils.StockAPIDiffCallback
import com.mityushov.investor.utils.setArrowImageRedOrGreen
import com.mityushov.investor.utils.setTextColorRedOrGreen
import timber.log.Timber

class StockListAdapter(private val navigator: Navigator):
    ListAdapter<StockAPI, StockListAdapter.StockItemViewHolder>(StockAPIDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockListAdapter.StockItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.stock_item, parent, false)
        return StockItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: StockListAdapter.StockItemViewHolder, position: Int) {
        val stock = getItem(position)
        holder.bind(stock, navigator)
    }

    // ViewHolder
    inner class StockItemViewHolder(itemView: View) : RecyclerView.ViewHolder(
        itemView), View.OnClickListener {
        private lateinit var stock: StockAPI
        private val binding: StockItemBinding = StockItemBinding.bind(itemView)
        private lateinit var navigator: Navigator

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(stock: StockAPI, navigator: Navigator) {
            this.stock = stock
            this.navigator = navigator

            with(binding) {
                stockItemCorpNameTv.text = stock.getName()
                stockItemCurrentPriceTv.text = String.format("%.2f", stock.getCurrentCurrency())

                with(stockItemDailyChangeTv) {
                    val value = stock.getDailyChange()
                    text = String.format("%.2f (%.2f%%)", value, stock.getDailyChangeInPercent())
                    setTextColorRedOrGreen(value, this)
                }

                with(stockItemIv) {
                    val value = stock.getDailyChange()
                    setImageResource(setArrowImageRedOrGreen(value, this))
                }
            }
        }

        override fun onClick(v: View?) {
            Timber.i("onClick() is called, stockId is ${stock.getId()}")
            navigator.onStockSelected(stock.getId())
        }
    }

}