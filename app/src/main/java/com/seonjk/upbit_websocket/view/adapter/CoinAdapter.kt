package com.seonjk.upbit_websocket.view.adapter

import android.icu.text.DecimalFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.seonjk.upbit_websocket.databinding.CoinInfoItemBinding
import com.seonjk.upbit_websocket.data.model.CoinListItem

class CoinAdapter
    : ListAdapter<CoinListItem, CoinAdapter.CoinItemHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinItemHolder {
        val binding =
            CoinInfoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinItemHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinItemHolder, position: Int) {
        holder.code.text = currentList[position].code
        holder.tradePrice.text = currentList[position].tradePrice.toString()
        holder.accPrice.text = DecimalFormat("#,###")
            .format((currentList[position].accTradePrice24h / 1_000_000))
            .toString()
        holder.accPrice.append("백만")
    }

    override fun getItemCount(): Int = currentList.size

    inner class CoinItemHolder(binding: CoinInfoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val code = binding.coinCode
        val tradePrice = binding.tradePrice
        val accPrice = binding.accPrice
    }
}