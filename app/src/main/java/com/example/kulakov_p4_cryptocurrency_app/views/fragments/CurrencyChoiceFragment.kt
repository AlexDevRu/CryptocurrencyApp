package com.example.kulakov_p4_cryptocurrency_app.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.domain.models.Currency
import com.example.kulakov_p4_cryptocurrency_app.R
import com.example.kulakov_p4_cryptocurrency_app.adapters.CurrencyChoiceAdapter
import com.example.kulakov_p4_cryptocurrency_app.adapters.CurrencyLoadStateAdapter
import com.example.kulakov_p4_cryptocurrency_app.databinding.FragmentCurrencyChoiceBinding
import com.example.kulakov_p4_cryptocurrency_app.view_models.ConverterVM
import com.example.kulakov_p4_cryptocurrency_app.view_models.CurrencyChoiceVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException

@AndroidEntryPoint
class CurrencyChoiceFragment: BaseFragment<FragmentCurrencyChoiceBinding>
    (R.layout.fragment_currency_choice) {

    private val viewModel: CurrencyChoiceVM by viewModels()
    private val sharedViewModel: ConverterVM by activityViewModels()

    private val adapter = CurrencyChoiceAdapter(::clickCurrencyHandler)

    private var getAllCurrenciesJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        val header = CurrencyLoadStateAdapter { adapter.retry() }
        val footer = CurrencyLoadStateAdapter { adapter.retry() }

        adapter.addLoadStateListener { state ->
            val isListEmpty = state.refresh is LoadState.NotLoading && adapter.itemCount == 0
            viewModel.isResultEmpty.set(isListEmpty)

            viewModel.listIsShown.set(state.source.refresh is LoadState.NotLoading || state.mediator?.refresh is LoadState.NotLoading)
            viewModel.loading.set(state.source.refresh is LoadState.Loading || state.mediator?.refresh is LoadState.Loading)

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

        observe()
    }

    private fun clickCurrencyHandler(currency: Currency?) {
        if(sharedViewModel.setFromCurrency)
            sharedViewModel.fromCurrency.get()?.currency = currency
        else
            sharedViewModel.toCurrency.get()?.currency = currency

        val action = CurrencyChoiceFragmentDirections.actionCurrencyChoiceFragmentToConverterFragment()
        findNavController().navigate(action)
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