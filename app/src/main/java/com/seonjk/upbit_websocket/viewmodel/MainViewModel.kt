package com.seonjk.upbit_websocket.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.seonjk.upbit_websocket.data.local.CoinDao
import com.seonjk.upbit_websocket.data.model.CoinListItem
import com.seonjk.upbit_websocket.data.repository.LocalRepository
import com.seonjk.upbit_websocket.data.repository.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException

class MainViewModel(private val coinDao: CoinDao): ViewModel() {

    private val TAG = "MainViewModel"

    /** DB Repository */
    private val localRepository by lazy { LocalRepository(coinDao) }
    /** WebSocket Repository */
    private val remoteRepository by lazy { RemoteRepository(this) }

    private val _socketStatus = MutableStateFlow(false)
    /** 웹소켓 상태 */
    val socketStatus: StateFlow<Boolean> = _socketStatus

    private val _coinList = MutableStateFlow(listOf<CoinListItem>())
    /** DB에서 가져온 코인 리스트 */
    val coinList: StateFlow<List<CoinListItem>> = _coinList

    private val _sortedByCurrentPrice = MutableStateFlow(false)
    val sortedByCurrentPrice: StateFlow<Boolean> = _sortedByCurrentPrice

    private val _sortedBydAccPrice = MutableStateFlow(false)
    val sortedByAccPrice: StateFlow<Boolean> = _sortedBydAccPrice


    /**
     * 웹소켓 연결
     * */
    suspend fun connectWebsocket() = withContext(Dispatchers.IO) {
        try {
            remoteRepository.connect()
            Log.d(TAG, "connectWebsocket() connected")
        } catch (e: IOException) {
            Log.d(TAG, "connectWebsocket() connection failed : ${e.message}")
        }
    }

    /**
     * 웹소켓 연결 해제
     * */
    fun disconnectWebsocket() = viewModelScope.launch {
        try {
            remoteRepository.disconnect()
            Log.d(TAG, "disconnectWebsocket()")
        } catch (e: IOException) {
            Log.d(TAG, "disconnectWebsocket() failed : ${e.message}")
        }
    }

    /**
     * 웹소켓 상태 설정
     * */
    fun setSocketStatus(status: Boolean) = viewModelScope.launch(Dispatchers.Main) {
        Log.d(TAG, "setSocketStatus() status=$status")
        _socketStatus.value = status
    }

    /**
     * 웹소켓으로 전달된 coin 정보를 DB에 업데이트하여 저장한다.
     * */
    fun setMessage(item: CoinListItem) = viewModelScope.launch(Dispatchers.IO) {
        if (item.code.isNotBlank()) {
            Log.d(TAG, "setMessage() item=$item")
            localRepository.upsert(item)
        }
    }

    fun setSortedByCurrentPrice(status: Boolean) {
        _sortedByCurrentPrice.value = status
    }


    fun setSortedByAccPrice(status: Boolean) {
        _sortedBydAccPrice.value = status
    }

    /**
     * 검색창에 입력한 문자열을 기반으로 DB에 검색하여 리스트를 반환
     * text가 null이면 모든 코인 리스트를 반환
     *
     * @param text 검색창에 입력한 문자열
     * */
    fun selectItems(text: String = "") = viewModelScope.launch(Dispatchers.IO) {
        localRepository.selectItems(text)
            .map {
                if (_sortedByCurrentPrice.value) {
                    it.sortedBy { it.tradePrice }
                }
                if (_sortedBydAccPrice.value) {
                    it.sortedBy { it.accTradePrice24h }
                }
                it
            }
            .collect {
            _coinList.value = it
        }
    }
}

/**
 * 안전하게 Dao를 인자로 받으며 뷰모델을 생성하도록 도와주는 Factory 클래스
 * */
class MainViewModelFactory(private val coinDao: CoinDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(coinDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}