package com.project.bankingapp.feature.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.project.bankingapp.R
import com.project.bankingapp.databinding.ItemTransactionBinding
import com.project.bankingapp.feature.dashboard.Transaction
import com.project.bankingapp.feature.dashboard.TransactionType

class TransactionItemAdapter(private val trxItems: List<Transaction>) :
    RecyclerView.Adapter<TransactionItemAdapter.TransactionItemVH>() {

    class TransactionItemVH(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(trx: Transaction) = with(binding) {
            tvName.text = trx.account.name
            tvAccount.text = trx.account.no

            val context = root.context
            if (trx.type == TransactionType.Expense) {
                tvAmount.apply {
                    text = context.getString(R.string.dashboard_trx_item_expense, trx.amount)
                    setTextColor(ContextCompat.getColor(context, R.color.grey))
                }
            } else {
                tvAmount.apply {
                    text = context.getString(R.string.dashboard_trx_item_income, trx.amount)
                    setTextColor(ContextCompat.getColor(context, R.color.green_500))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionItemVH {
        val binding = ItemTransactionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return TransactionItemVH(binding)
    }

    override fun onBindViewHolder(holder: TransactionItemVH, position: Int) {
        holder.bind(trxItems[position])
    }

    override fun getItemCount(): Int = trxItems.size
}