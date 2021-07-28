package com.example.kulakov_p4_cryptocurrency_app.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
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

        adapter.addLoadStateListener { state ->
            viewModel.loading.set(state.refresh == LoadState.Loading)
            if(state.refresh is LoadState.Error) {
                Log.e("asd", "error ${(state.refresh as LoadState.Error).error.localizedMessage}")
                val exception = (state.refresh as LoadState.Error).error
                val error = when(exception) {
                    is HttpException -> exception.message()
                    else -> exception.localizedMessage.orEmpty()
                }
                viewModel.error.set(error)
            } else {
                viewModel.error.set(null)
            }
        }

        binding.currencyList.adapter = adapter.withLoadStateHeaderAndFooter(
            CurrencyLoadStateAdapter { adapter.retry() },
            CurrencyLoadStateAdapter { adapter.retry() }
        )

        getAllCurrencies()

        /*ArrayAdapter.createFromResource(
            requireContext(),
            R.array.currency_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.typesAdapter = adapter
        }*/
    }

    private fun getAllCurrencies() {
        viewModel.setCurrencies.observe(viewLifecycleOwner) {
            getAllCurrenciesJob?.cancel()
            getAllCurrenciesJob = lifecycleScope.launch(Dispatchers.IO) {
                viewModel.getCurrencies().collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }
}