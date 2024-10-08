package com.seonjk.upbit_websocket.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.seonjk.upbit_websocket.UpbitApplication
import com.seonjk.upbit_websocket.data.model.SortType
import com.seonjk.upbit_websocket.databinding.ActivityMainBinding
import com.seonjk.upbit_websocket.view.adapter.CoinAdapter
import com.seonjk.upbit_websocket.viewmodel.MainViewModel
import com.seonjk.upbit_websocket.viewmodel.MainViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as UpbitApplication).db.coinDao())
    }

    private val coinAdapter = CoinAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subscribeUi()
        setRecyclerView()

        onButtonClick()
    }

    override fun onDestroy() {
        mainViewModel.disconnectWebsocket()
        super.onDestroy()
    }

    private fun subscribeUi() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                mainViewModel.socketStatus.collectLatest { status ->
                    Log.d(TAG, "subscribeUi() status=$status")
                    if (!status) {
                        connectWebsocket()
                    } else {
                        mainViewModel.selectItems()
                    }
                }
            }
        }

        binding.coinSearch.addTextChangedListener {
            mainViewModel.setSearchText(binding.coinSearch.text.toString())
            mainViewModel.selectItems()
        }
    }

    private fun connectWebsocket() = runBlocking {
        mainViewModel.connectWebsocket()
    }

    private fun setRecyclerView() = with(binding) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = coinAdapter
        }

        lifecycleScope.launch {
            mainViewModel.coinList.collectLatest { list ->
                coinAdapter.submitList(list)
            }
        }
    }

    private fun onButtonClick() = with(binding.listTitle) {
        currentPrice.setOnClickListener { clickCurrentPriceTitle() }
        accPrice.setOnClickListener { clickAccPriceTitle() }
    }

    private fun clickCurrentPriceTitle() {
        Log.d(TAG, "clickCurrentPriceTitle()")
        when (mainViewModel.sortedByCurrentPrice.value) {
            SortType.NONE -> mainViewModel.setSortedByCurrentPrice(SortType.ASCENDING)
            SortType.ASCENDING -> mainViewModel.setSortedByCurrentPrice(SortType.DESCENDING)
            SortType.DESCENDING -> mainViewModel.setSortedByCurrentPrice(SortType.NONE)
        }
        mainViewModel.setSortedByAccPrice(SortType.NONE)
        mainViewModel.selectItems()
    }

    private fun clickAccPriceTitle() {
        Log.d(TAG, "clickAccPriceTitle()")
        when (mainViewModel.sortedByAccPrice.value) {
            SortType.NONE -> mainViewModel.setSortedByAccPrice(SortType.ASCENDING)
            SortType.ASCENDING -> mainViewModel.setSortedByAccPrice(SortType.DESCENDING)
            SortType.DESCENDING -> mainViewModel.setSortedByAccPrice(SortType.NONE)
        }
        mainViewModel.setSortedByCurrentPrice(SortType.NONE)
        mainViewModel.selectItems()
    }
}