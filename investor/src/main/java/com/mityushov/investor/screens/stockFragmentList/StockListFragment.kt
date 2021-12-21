package com.mityushov.investor.screens.stockFragmentList

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mityushov.investor.databinding.FragmentStockListBinding
import com.mityushov.investor.navigation.navigator
import com.mityushov.investor.models.CacheStockPurchase
import com.mityushov.investor.utils.setTextColorRedOrGreen
import timber.log.Timber

class StockListFragment : Fragment() {

    private lateinit var binding: FragmentStockListBinding
    private lateinit var stocksRecyclerView: RecyclerView
    private lateinit var adapter: StockListAdapter
    private lateinit var stLstViewModel: StockListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        adapter = StockListAdapter(this.navigator())

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
            this.navigator().onBuyButtonPressed()
        }
/*
        binding.fragmentStockListRecyclerview.setOnTouchListener(object: OnSwipeTouchListener(this.activity) {
            override fun onSwipeBottom() {
                super.onSwipeBottom()
                Toast.makeText(context, "bottom", Toast.LENGTH_LONG).show()
            }
        })

 */
//        binding.refreshBtn.setOnClickListener {
//            Timber.d("refresh is clicked")
//            stLstViewModel.refreshScreen()
//        }

    }

    private fun updateUI(stocks: List<CacheStockPurchase>) {
        adapter.submitList(stocks)
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
