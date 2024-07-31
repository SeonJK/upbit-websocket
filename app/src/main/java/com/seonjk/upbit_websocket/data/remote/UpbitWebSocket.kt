package com.seonjk.upbit_websocket.data.remote

import android.util.Log
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request

class UpbitWebSocket() {

    private val TAG = "UpbitWebSocket"
    private val URL = "wss://api.upbit.com/websocket/v1"

    private val client = OkHttpClient.Builder().build()
    private val request = Request.Builder()
        .url(URL)
        .get()
        .build()
    private val response = client.newCall(request).execute()

    @OptIn(DelicateCoroutinesApi::class)
    fun connect(listener: UpbitWebSocketListener)= GlobalScope.launch {
        Log.d(TAG, "connect()")
        client.newWebSocket(request, listener)
    }

    fun disconnect() = client.dispatcher.executorService.shutdown()
}