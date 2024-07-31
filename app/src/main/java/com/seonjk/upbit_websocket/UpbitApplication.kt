package com.seonjk.upbit_websocket

import android.app.Application
import com.seonjk.upbit_websocket.data.local.AppDatabase

class UpbitApplication : Application() {

    val db: AppDatabase by lazy { AppDatabase.getInstance(this) }

}