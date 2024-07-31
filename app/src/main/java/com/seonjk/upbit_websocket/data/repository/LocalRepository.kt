package com.seonjk.upbit_websocket.data.repository

import com.seonjk.upbit_websocket.data.local.CoinDao
import com.seonjk.upbit_websocket.data.model.CoinListItem
import kotlinx.coroutines.flow.Flow

class LocalRepository(private val coinDao: CoinDao) {

    suspend fun upsert(item: CoinListItem) = coinDao.upsert(item)

    fun selectItems(text: String): Flow<List<CoinListItem>> =
        if (text.isBlank()) coinDao.selectAll()
        else coinDao.selectItems(text)
}