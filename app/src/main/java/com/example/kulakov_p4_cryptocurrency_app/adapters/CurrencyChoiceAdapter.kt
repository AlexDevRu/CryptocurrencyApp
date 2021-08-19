package com.example.kulakov_p4_cryptocurrency_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Currency
import com.example.kulakov_p4_cryptocurrency_app.R
import com.example.kulakov_p4_cryptocurrency_app.adapters.diff_util.CurrencyDiff
import com.example.kulakov_p4_cryptocurrency_app.databinding.ItemCurrencyChoiceBinding
import com.example.kulakov_p4_cryptocurrency_app.view_models.items.CurrencyVM

class CurrencyChoiceAdapter(private val onCurrencyClick: (currency: Currency?) -> Unit):
    PagingDataAdapter<Currency, CurrencyChoiceAdapter.CurrencyHolder>(CurrencyDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyHolder {
        val binding = DataBindingUtil.inflate<ItemCurrencyChoiceBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_currency_choice,
            parent,
            false
        )

        return CurrencyHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyHolder, position: Int) {
        getItem(position)?.let { photoItem -> holder.bind(photoItem) }
    }

    interface Handler {
        fun onItemClick()
    }

    inner class CurrencyHolder(private val binding: ItemCurrencyChoiceBinding)
        : RecyclerView.ViewHolder(binding.root) {
        init {
            val viewModel = CurrencyVM()
            binding.viewModel = viewModel
        }

        fun bind(currency: Currency) {
            binding.apply {
                viewModel?.currency = currency
                handler = object: Handler {
                    override fun onItemClick() {
                        onCurrencyClick(binding.viewModel?.currency)
                    }
                }
                executePendingBindings()
            }
        }
    }
}