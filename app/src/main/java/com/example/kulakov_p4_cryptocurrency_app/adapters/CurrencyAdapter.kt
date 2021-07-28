package com.example.kulakov_p4_cryptocurrency_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Currency
import com.example.kulakov_p4_cryptocurrency_app.R
import com.example.kulakov_p4_cryptocurrency_app.databinding.ItemCurrencyBinding
import com.example.kulakov_p4_cryptocurrency_app.view_models.items.CurrencyVM

class CurrencyAdapter: PagingDataAdapter<Currency, CurrencyAdapter.CurrencyHolder>(CurrencyDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyHolder {
        val binding = DataBindingUtil.inflate<ItemCurrencyBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_currency,
            parent,
            false
        )

        return CurrencyHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyHolder, position: Int) {
        getItem(position)?.let { photoItem -> holder.bind(photoItem) }
    }

    inner class CurrencyHolder(private val binding: ItemCurrencyBinding)
        : RecyclerView.ViewHolder(binding.root) {
        init {
            val viewModel = CurrencyVM()
            binding.viewModel = viewModel
        }

        fun bind(currency: Currency) {
            binding.apply {
                viewModel?.currency = currency
                /*delegate = object: Delegate {
                    override fun onItemClick() {
                        Navigator.getInstance().searchFragmentNavigator.showPhotoDetail(photoItem)
                    }
                }*/
                executePendingBindings()
            }
        }
    }

    class CurrencyDiff : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Currency, newItem: Currency) = oldItem == newItem
    }
}