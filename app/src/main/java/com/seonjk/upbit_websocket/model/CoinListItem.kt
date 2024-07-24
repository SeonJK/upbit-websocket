package com.seonjk.upbit_websocket.model

/**
 * RecyclerView Item 으로 사용할 Data class
 * */
data class CoinListItem(
    val code: String,
    val tradePrice: Double,
    val accTradePrice24h: Double
)
