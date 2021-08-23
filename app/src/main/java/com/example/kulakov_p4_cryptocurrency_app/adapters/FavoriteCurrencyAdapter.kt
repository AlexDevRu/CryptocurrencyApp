package com.example.kulakov_p4_cryptocurrency_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Currency
import com.example.kulakov_p4_cryptocurrency_app.R
import com.example.kulakov_p4_cryptocurrency_app.adapters.diff_util.CurrencyDiff
import com.example.kulakov_p4_cryptocurrency_app.databinding.ItemFavoriteCurrencyBinding
import com.example.kulakov_p4_cryptocurrency_app.parcelable.mappers.CurrencyArgMapper
import com.example.kulakov_p4_cryptocurrency_app.view_models.items.CurrencyVM
import com.example.kulakov_p4_cryptocurrency_app.views.fragments.FavoriteFragmentDirections

class FavoriteCurrencyAdapter(private val deleteHandler: (Currency) -> Unit)
    : ListAdapter<Currency, FavoriteCurrencyAdapter.CurrencyHolder>(CurrencyDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyHolder {
        val binding = DataBindingUtil.inflate<ItemFavoriteCurrencyBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_favorite_currency,
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
        fun onDelete()
    }

    inner class CurrencyHolder(private val binding: ItemFavoriteCurrencyBinding)
        : RecyclerView.ViewHolder(binding.root) {

        init {
            val viewModel = CurrencyVM()
            binding.viewModel = viewModel
        }

        fun bind(currency: Currency) {
            binding.apply {
                viewModel?.currency = currency
                currencyImageView.transitionName = "currencyImageView${currency.id}"
                currencyTitle.transitionName = "currencyTitle${currency.id}"
                currencyPrice.transitionName = "currencyPrice${currency.id}"
                currencyPercentChange1h.transitionName = "percentChange1h${currency.id}"
                handler = object: Handler {
                    override fun onItemClick() {
                        val extras = FragmentNavigatorExtras(
                            binding.currencyImageView to currencyImageView.transitionName,
                            binding.currencyTitle to currencyTitle.transitionName,
                            binding.currencyPrice to currencyPrice.transitionName,
                            binding.currencyPercentChange1h to currencyPercentChange1h.transitionName
                        )
                        val arg = CurrencyArgMapper.fromModel(viewModel?.currency!!)
                        val action = FavoriteFragmentDirections.actionFavoriteFragmentToCurrencyDetailFragment(arg)
                        itemView.findNavController().navigate(action, extras)
                    }

                    override fun onDelete() {
                        viewModel?.favorite = false
                        deleteHandler(viewModel?.currency!!)
                        notifyItemRemoved(absoluteAdapterPosition)
                        notifyItemRangeChanged(absoluteAdapterPosition, itemCount)
                    }
                }
                executePendingBindings()
            }
        }
    }
}