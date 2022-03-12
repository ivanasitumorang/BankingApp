package com.project.bankingapp.feature.dashboard.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.bankingapp.databinding.ItemTrxHistoryBinding
import com.project.bankingapp.feature.dashboard.TransactionHistory

class TransactionHistoryAdapter : RecyclerView.Adapter<TransactionHistoryAdapter.TrxHistoryVH>() {

    private val _trxHistoryList = mutableListOf<TransactionHistory>()

    @SuppressLint("NotifyDataSetChanged")
    fun setTrxHistoryList(data: List<TransactionHistory>) {
        _trxHistoryList.clear()
        _trxHistoryList.addAll(data)
        notifyDataSetChanged()
    }

    class TrxHistoryVH(private val binding: ItemTrxHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(trx: TransactionHistory) = with(binding) {
            tvDate.text = trx.dateString
            val itemAdapter = TransactionItemAdapter(trx.transactions)
            rvTransaction.adapter = itemAdapter
        }
    }

    override fun getItemCount(): Int = _trxHistoryList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrxHistoryVH {
        val binding = ItemTrxHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TrxHistoryVH(binding)
    }

    override fun onBindViewHolder(holder: TrxHistoryVH, position: Int) {
        holder.bind(_trxHistoryList[position])
    }
}