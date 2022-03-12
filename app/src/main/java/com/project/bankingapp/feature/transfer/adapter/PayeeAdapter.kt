package com.project.bankingapp.feature.transfer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.bankingapp.common.dto.Account
import com.project.bankingapp.databinding.ItemPayeeBinding

class PayeeAdapter(
    private val clickListener: (Account) -> Unit
) : ListAdapter<Account, PayeeAdapter.PayeeVH>(PayeeDiffItemCallback) {

    class PayeeVH(private val binding: ItemPayeeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(payee: Account, clickListener: (Account) -> Unit) = with(binding) {
            tvPayeeName.text = payee.name
            tvPayeeNo.text = payee.no
            root.setOnClickListener { clickListener(payee) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayeeVH {
        val binding = ItemPayeeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return PayeeVH(binding)
    }

    override fun onBindViewHolder(holder: PayeeVH, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

}

object PayeeDiffItemCallback : DiffUtil.ItemCallback<Account>() {
    override fun areItemsTheSame(oldItem: Account, newItem: Account): Boolean =
        oldItem.no == newItem.no

    override fun areContentsTheSame(oldItem: Account, newItem: Account): Boolean =
        oldItem == newItem

}