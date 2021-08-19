package com.example.kulakov_p4_cryptocurrency_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Currency
import com.example.kulakov_p4_cryptocurrency_app.R
import com.example.kulakov_p4_cryptocurrency_app.adapters.diff_util.CurrencyDiff
import com.example.kulakov_p4_cryptocurrency_app.databinding.ItemCurrencyBinding
import com.example.kulakov_p4_cryptocurrency_app.parcelable.mappers.CurrencyArgMapper
import com.example.kulakov_p4_cryptocurrency_app.view_models.items.CurrencyVM
import com.example.kulakov_p4_cryptocurrency_app.views.fragments.MainFragmentDirections

class CurrencyAdapter(private val updateFavoriteHandler: (Currency) -> Unit)
    : PagingDataAdapter<Currency, CurrencyAdapter.CurrencyHolder>(CurrencyDiff()) {

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

    interface Handler {
        fun onItemClick()
        fun onUpdateFavorite()
    }

    inner class CurrencyHolder(private val binding: ItemCurrencyBinding)
        : RecyclerView.ViewHolder(binding.root) {

        val viewModel = CurrencyVM()

        init {
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
                        val action = MainFragmentDirections.actionMainFragmentToCurrencyDetailFragment(arg)
                        itemView.findNavController().navigate(action, extras)
                    }

                    override fun onUpdateFavorite() {
                        viewModel?.favorite = !viewModel?.favorite!!
                        updateFavoriteHandler(viewModel?.currency!!)
                    }
                }
                executePendingBindings()
            }
        }
    }
}