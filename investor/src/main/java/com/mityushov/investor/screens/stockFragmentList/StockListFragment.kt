package com.mityushov.investor.screens.stockFragmentList

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mityushov.investor.R
import com.mityushov.investor.databinding.FragmentStockListBinding
import com.mityushov.investor.databinding.StockItemBinding
import com.mityushov.investor.models.StockAPI
import com.mityushov.investor.screens.aboutFragment.AboutFragment
import java.util.*
import com.mityushov.investor.utils.setTextColorRedOrGreen
import timber.log.Timber

class StockListFragment : Fragment() {

    interface Callbacks {
        fun onStockSelected(stockId: UUID)
        fun onBuyButtonPressed()
    }

    private var callbacks: Callbacks? = null
    private lateinit var binding: FragmentStockListBinding
    private lateinit var stocksRecyclerView: RecyclerView
    private var adapter: StockListAdapter = StockListAdapter(emptyList())
    private lateinit var stLstViewModel: StockListViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentStockListBinding.inflate(inflater, container, false)

        stLstViewModel = ViewModelProvider(this).get(StockListViewModel::class.java)

        stocksRecyclerView = binding.fragmentStockListRecyclerview.also {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = adapter

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stLstViewModel.list.observe(viewLifecycleOwner, { list ->
            updateUI(list)
        })

        binding.fragmentStockListBuyBtn.setOnClickListener {
            callbacks?.onBuyButtonPressed()
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    // 1) create ViewHolder
    private inner class StockItemViewHolder(itemView: View) : RecyclerView.ViewHolder(
        itemView), View.OnClickListener {
        private lateinit var stock: StockAPI
        private val binding: StockItemBinding = StockItemBinding.bind(itemView)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(stock: StockAPI) {
            this.stock = stock

            with(binding) {
                stockItemCorpNameTv.text = stock.getName()

                with(stockItemCurrentPriceTv) {
                    text = String.format("%.2f", stock.getCurrentCurrency())
                }

                with(stockItemDailyChangeTv) {
                    val value = stock.getDailyChange()
                    text = String.format("%.2f (%.2f%%)", value, stock.getDailyChangeInPercent())
                    setTextColorRedOrGreen(value, this)
                }
            }
        }

        override fun onClick(v: View?) {
            Timber.i("onClick() is called, stockId is ${stock.getId()}")
            callbacks?.onStockSelected(stock.getId())
        }
    }
    // 2) create Adapter
    private inner class StockListAdapter(val stocks: List<StockAPI>) :
        RecyclerView.Adapter<StockItemViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockItemViewHolder {
            val view = layoutInflater.inflate(R.layout.stock_item, parent, false)
            return StockItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: StockItemViewHolder, position: Int) {
            val stock = stocks[position]
            holder.bind(stock)
        }

        override fun getItemCount(): Int {
            return stocks.size
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.overflow_menu_about_fragment -> {
                val fragment = AboutFragment()
                this.activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.replace(R.id.fragment_container, fragment)
                    ?.addToBackStack(null)
                    ?.commit()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun updateUI(stocks: List<StockAPI>) {
        adapter = StockListAdapter(stocks)
        stocksRecyclerView.adapter = adapter
        binding.fragmentStockListSummaryProfitValueTV.apply {
            val value = stLstViewModel.getTotalProfit()
            text = String.format("%.2f", value)
            setTextColorRedOrGreen(value, this)
        }

        binding.fragmentStockListDailyProfitValueTV.apply {
            val value = stLstViewModel.getDailyProfit()
            text = String.format("%.2f", value)
            setTextColorRedOrGreen(value, this)
        }
    }
}