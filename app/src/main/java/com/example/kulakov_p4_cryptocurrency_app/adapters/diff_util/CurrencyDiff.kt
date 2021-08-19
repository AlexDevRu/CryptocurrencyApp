package com.example.kulakov_p4_cryptocurrency_app.adapters.diff_util

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.models.Currency

class CurrencyDiff : DiffUtil.ItemCallback<Currency>() {
    override fun areItemsTheSame(oldItem: Currency, newItem: Currency) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Currency, newItem: Currency) = oldItem == newItem
}