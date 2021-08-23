package com.example.kulakov_p4_cryptocurrency_app.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.kulakov_p4_cryptocurrency_app.R
import com.example.kulakov_p4_cryptocurrency_app.adapters.FavoriteCurrencyAdapter
import com.example.kulakov_p4_cryptocurrency_app.databinding.FragmentFavoriteBinding
import com.example.kulakov_p4_cryptocurrency_app.utils.SearchDestination
import com.example.kulakov_p4_cryptocurrency_app.view_models.FavoriteVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment: BaseFragment<FragmentFavoriteBinding>
    (R.layout.fragment_favorite) {

    private val viewModel by viewModels<FavoriteVM>()

    private lateinit var adapter: FavoriteCurrencyAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        adapter = FavoriteCurrencyAdapter(viewModel::deleteFromFavorite)

        binding.favoriteList.adapter = adapter

        binding.favoriteList.prepareToSharedTransition(this)

        initToolbar(binding.toolbar, R.string.favorite)

        observe()
    }

    private fun observe() {
        viewModel.setFavoriteCurrencies.observe(viewLifecycleOwner) {
            binding.favoriteList.scrollToTop()
            adapter.submitList(viewModel.favoriteList)
        }

        mainActivityVM.searchableLiveData.observe(viewLifecycleOwner) {
            if(it?.destination == SearchDestination.FAVORITE) {
                viewModel.searchQuery.set(it.query)
            }
        }
    }
}