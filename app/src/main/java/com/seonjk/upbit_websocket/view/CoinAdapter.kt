package com.seonjk.upbit_websocket.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seonjk.upbit_websocket.databinding.CoinInfoItemBinding
import com.seonjk.upbit_websocket.model.CoinListItem

class CoinAdapter(val dataList: ArrayList<CoinListItem>) : RecyclerView.Adapter<CoinAdapter.CoinItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinItemHolder {
        val binding =
            CoinInfoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinItemHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinItemHolder, position: Int) {
        holder.code.text = dataList[position].code
        holder.tradePrice.text = dataList[position].tradePrice.toString()
        holder.accPrice.text = dataList[position].accTradePrice24h.toString()
    }

    override fun getItemCount(): Int = dataList.size

    inner class CoinItemHolder(binding: CoinInfoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val code = binding.coinCode
        val tradePrice = binding.tradePrice
        val accPrice = binding.accPrice
    }
}