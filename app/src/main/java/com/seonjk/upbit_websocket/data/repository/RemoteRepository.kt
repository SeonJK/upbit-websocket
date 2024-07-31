package com.seonjk.upbit_websocket.data.repository

import com.seonjk.upbit_websocket.data.remote.UpbitWebSocket
import com.seonjk.upbit_websocket.data.remote.UpbitWebSocketListener
import com.seonjk.upbit_websocket.viewmodel.MainViewModel

class RemoteRepository(
    viewModel: MainViewModel
) {

    private var webSocket: UpbitWebSocket? = null
    private val websocketListener = UpbitWebSocketListener(viewModel)

    fun connect() {
        webSocket = UpbitWebSocket()
        webSocket?.connect(websocketListener)
    }

    fun disconnect() {
        webSocket?.disconnect()
        webSocket = null
    }
}