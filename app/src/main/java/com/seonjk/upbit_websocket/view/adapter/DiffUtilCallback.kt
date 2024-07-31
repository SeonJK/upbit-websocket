package com.seonjk.upbit_websocket.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.seonjk.upbit_websocket.data.model.CoinListItem

class DiffUtilCallback() : DiffUtil.ItemCallback<CoinListItem>() {

    override fun areItemsTheSame(oldItem: CoinListItem, newItem: CoinListItem): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: CoinListItem, newItem: CoinListItem): Boolean =
        oldItem.tradePrice == newItem.tradePrice
}