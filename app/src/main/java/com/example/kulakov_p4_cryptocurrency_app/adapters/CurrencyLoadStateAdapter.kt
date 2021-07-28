package com.example.kulakov_p4_cryptocurrency_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kulakov_p4_cryptocurrency_app.R
import com.example.kulakov_p4_cryptocurrency_app.databinding.ItemErrorBinding
import com.example.kulakov_p4_cryptocurrency_app.databinding.ItemLoadingBinding

class CurrencyLoadStateAdapter(private val retry: () -> Unit): LoadStateAdapter<CurrencyLoadStateAdapter.ItemViewHolder>() {
    abstract class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(loadState: LoadState)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ItemViewHolder {
        return when(loadState) {
            LoadState.Loading -> {
                val binding = DataBindingUtil.inflate<ItemLoadingBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_loading,
                    parent,
                    false
                )
                ProgressViewHolder(binding)
            }
            is LoadState.Error -> {
                val binding = DataBindingUtil.inflate<ItemErrorBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_error,
                    parent,
                    false
                )
                ErrorViewHolder(binding)
            }
            is LoadState.NotLoading -> error("Not supported")
        }
    }

    class ProgressViewHolder internal constructor(
        binding: ItemLoadingBinding
    ) : ItemViewHolder(binding.root) {

        override fun bind(loadState: LoadState) {
            // Do nothing
        }
    }

    inner class ErrorViewHolder internal constructor(
        private val binding: ItemErrorBinding
    ) : ItemViewHolder(binding.root) {

        override fun bind(loadState: LoadState) {
            require(loadState is LoadState.Error)
            binding.apply {
                message = ObservableField(loadState.error.localizedMessage)
                onRetryClick = retry
                executePendingBindings()
            }
        }
    }
}