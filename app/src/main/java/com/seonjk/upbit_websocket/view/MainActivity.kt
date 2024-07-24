package com.seonjk.upbit_websocket.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.seonjk.upbit_websocket.databinding.ActivityMainBinding
import com.seonjk.upbit_websocket.model.CoinListItem

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val codeList = arrayListOf(
        CoinListItem("KRW-SAND",93204000.0, 263816.0),
        CoinListItem("KRW-BTC",93204000.0, 263816.0),
        CoinListItem("KRW-XRP",93204000.0, 263816.0),
        CoinListItem("KRW-SOL",93204000.0, 263816.0),
        CoinListItem("KRW-ETH",93204000.0, 263816.0),
        CoinListItem("KRW-SHIB",93204000.0, 263816.0),
        CoinListItem("KRW-SEI",93204000.0, 263816.0),
        CoinListItem("KRW-NEAR",93204000.0, 263816.0),
        CoinListItem("KRW-ID",93204000.0, 263816.0),
        CoinListItem("KRW-AVAX",93204000.0, 263816.0),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRecyclerView()
    }
    private fun setRecyclerView() = with(binding) {
        // TODO: WebSocket에서 가져온 viewmodel list로 바꾸기
        recyclerView.adapter = CoinAdapter(codeList)
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
    }
}