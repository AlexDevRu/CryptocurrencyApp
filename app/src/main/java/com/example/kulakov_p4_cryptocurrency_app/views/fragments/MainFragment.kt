package com.example.kulakov_p4_cryptocurrency_app.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.kulakov_p4_cryptocurrency_app.R
import com.example.kulakov_p4_cryptocurrency_app.adapters.CurrencyAdapter
import com.example.kulakov_p4_cryptocurrency_app.adapters.CurrencyLoadStateAdapter
import com.example.kulakov_p4_cryptocurrency_app.databinding.FragmentMainBinding
import com.example.kulakov_p4_cryptocurrency_app.view_models.MainVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException

@AndroidEntryPoint
class MainFragment: BaseFragment<FragmentMainBinding>
    (R.layout.fragment_main) {

    private val viewModel: MainVM by viewModels()

    private val adapter = CurrencyAdapter()

    private var getAllCurrenciesJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        val header = CurrencyLoadStateAdapter { adapter.retry() }
        val footer = CurrencyLoadStateAdapter { adapter.retry() }

        adapter.addLoadStateListener { state ->
            viewModel.loading.set(state.mediator?.refresh is LoadState.Loading)
            val isListEmpty = state.refresh is LoadState.NotLoading && adapter.itemCount == 0
            viewModel.isResultEmpty.set(isListEmpty)

            header.loadState = state.mediator
                ?.refresh
                ?.takeIf { it is LoadState.Error && adapter.itemCount > 0 }
                ?: state.prepend


            val errorState = state.source.append as? LoadState.Error
                ?: state.source.prepend as? LoadState.Error
                ?: state.append as? LoadState.Error
                ?: state.prepend as? LoadState.Error
            errorState?.let {
                Log.e("asd", "error ${it.error.localizedMessage}")
                val error = when(it.error) {
                    is HttpException -> (it.error as HttpException).message()
                    else -> it.error.localizedMessage.orEmpty()
                }
                viewModel.error.set(error)
            }
        }

        binding.currencyList.adapter = adapter.withLoadStateHeaderAndFooter(header, footer)

        binding.currencyList.apply {
            postponeEnterTransition()
            viewTreeObserver
                .addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
        }

        observe()
    }

    private fun observe() {
        viewModel.setCurrencies.observe(viewLifecycleOwner) {
            getAllCurrenciesJob?.cancel()
            getAllCurrenciesJob = lifecycleScope.launch(Dispatchers.IO) {
                viewModel.getCurrencies().collectLatest {
                    adapter.submitData(it)
                }
            }
        }

        viewModel.scrollListToPosition.observe(viewLifecycleOwner, {
            if(it != null)
                binding.currencyList.scrollToPosition(it)
        })
    }
}