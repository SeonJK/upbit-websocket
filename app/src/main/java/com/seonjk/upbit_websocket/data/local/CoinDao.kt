package com.seonjk.upbit_websocket.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.seonjk.upbit_websocket.data.model.CoinListItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {

    @Upsert
    suspend fun upsert(item: CoinListItem)

    @Query("SELECT * FROM CoinListItem")
    fun selectAll(): Flow<List<CoinListItem>>

    @Query("SELECT * FROM CoinListItem WHERE code LIKE '%'||:text||'%'")
    fun selectItems(text: String): Flow<List<CoinListItem>>

}