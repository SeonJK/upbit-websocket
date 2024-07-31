package com.seonjk.upbit_websocket.data.remote

import android.util.Log
import com.google.gson.Gson
import com.seonjk.upbit_websocket.data.model.Coin
import com.seonjk.upbit_websocket.data.model.CoinListItem
import com.seonjk.upbit_websocket.data.model.Ticket
import com.seonjk.upbit_websocket.data.model.Type
import com.seonjk.upbit_websocket.viewmodel.MainViewModel
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class UpbitWebSocketListener(
    private val viewModel: MainViewModel
) : WebSocketListener() {

    private val TAG = "UpbitWebSocketListener"
    private val UNIQUE_TICKET = "jkseon"

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        Log.d(TAG, "onOpen()")

        viewModel.setSocketStatus(true)
        webSocket.send(createTicket())
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
        Log.d(TAG, "onMessage: bytes=$bytes")

        val byteToString = bytes.toByteArray().decodeToString()
        Log.d(TAG, "onMessage: byteToString=$byteToString")
        val coin = Gson().fromJson(byteToString, Coin::class.java)
        Log.d(TAG, "onMessage: coin=$coin")

        val coinListItem = CoinListItem(
            code = coin.code,
            tradePrice = coin.trade_price,
            accTradePrice24h = coin.acc_trade_price_24h
        )
        Log.d(TAG, "onMessage: coinListItem=$coinListItem")
        viewModel.setMessage(coinListItem)
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        Log.d(TAG, "onClosing: code=$code, reason=$reason")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        Log.d(TAG, "onClosed: code=$code, reason=$reason")
        viewModel.setSocketStatus(false)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.d(TAG, "onFailure: t=${t.message}, response=$response")
        super.onFailure(webSocket, t, response)
    }

    fun createTicket(specificList: MutableList<String> = arrayListOf()): String {
        val ticket = Ticket(UNIQUE_TICKET)
        val codeList = arrayListOf(
            "KRW-SAND",
            "KRW-BTC",
            "KRW-XRP",
            "KRW-SOL",
            "KRW-ETH",
            "KRW-SHIB",
            "KRW-SEI",
            "KRW-NEAR",
            "KRW-ID",
            "KRW-AVAX",
            "KRW-DOGE",
            "KRW-SUI",
            "KRW-ETC",
            "KRW-BTG",
            "KRW-CTC",
            "KRW-ASTR",
            "KRW-MINA",
            "KRW-SC",
            "KRW-ZRX",
        )

        val type = if (specificList.isEmpty()) {
            Type(codes = codeList)
        } else {
            Type(codes = specificList)
        }

        return Gson().toJson(arrayListOf(ticket, type))
    }
}