package com.project.bankingapp.feature.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.bankingapp.databinding.ItemTrxHistoryBinding

class TransactionHistoryAdapter : RecyclerView.Adapter<TransactionHistoryAdapter.TrxHistoryVH>() {

    private val _trxHistoryList = mutableListOf<TransactionHistory>()

    class TrxHistoryVH(private val binding: ItemTrxHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(trx: TransactionHistory) {

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