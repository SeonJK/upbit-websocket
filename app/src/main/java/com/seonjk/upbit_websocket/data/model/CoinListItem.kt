package com.seonjk.upbit_websocket.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * RecyclerView Item 으로 사용할 Data class
 * */
@Entity
data class CoinListItem(
    /** 타입 */
    @PrimaryKey val code: String,
    /** 현재가 */
    @ColumnInfo(name = "trade_price") val tradePrice: Double,
    /** 24시간 누적 거래대금 */
    @ColumnInfo(name = "acc_trade_price_24h") val accTradePrice24h: Double
)
