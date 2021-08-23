package com.example.kulakov_p4_cryptocurrency_app.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.domain.repositories.remote.IFirebaseRepository
import com.example.kulakov_p4_cryptocurrency_app.R
import com.example.kulakov_p4_cryptocurrency_app.adapters.CurrencyAdapter
import com.example.kulakov_p4_cryptocurrency_app.adapters.CurrencyLoadStateAdapter
import com.example.kulakov_p4_cryptocurrency_app.databinding.FragmentMainBinding
import com.example.kulakov_p4_cryptocurrency_app.utils.SearchDestination
import com.example.kulakov_p4_cryptocurrency_app.view_models.MainVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment: BaseFragment<FragmentMainBinding>
    (R.layout.fragment_main) {

    private val viewModel: MainVM by viewModels()

    private lateinit var adapter: CurrencyAdapter

    private var getAllCurrenciesJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        adapter = CurrencyAdapter(viewModel::updateFavorite)

        val header = CurrencyLoadStateAdapter { adapter.retry() }
        val footer = CurrencyLoadStateAdapter { adapter.retry() }

        adapter.addLoadStateListener { state ->
            val isListEmpty = state.refresh is LoadState.NotLoading && adapter.itemCount == 0
            viewModel.listVM.isResultEmpty.set(isListEmpty)

            viewModel.listVM.loading.set(state.refresh is LoadState.Loading || state.mediator?.refresh is LoadState.Loading)

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
                viewModel.listVM.error.set(error)
            }
        }

        binding.currencyList.adapter = adapter.withLoadStateHeaderAndFooter(header, footer)

        binding.currencyList.prepareToSharedTransition(this)

        initToolbar(binding.toolbar, R.string.main)

        observeData()
    }

    @Inject
    lateinit var firebaseRepository: IFirebaseRepository

    private fun observeData() {
        internetObserver.observe(viewLifecycleOwner) {
            if(viewModel.isOnline.get() == it) return@observe

            viewModel.isOnline.set(it)

            if(!it) {
                showSnackBar(R.string.offline_message)
            }
        }

        viewModel.setCurrencies.observe(viewLifecycleOwner) {
            getAllCurrenciesJob?.cancel()
            getAllCurrenciesJob = lifecycleScope.launch {
                viewModel.getCurrencies().collectLatest {
                    adapter.submitData(it)
                }
            }
        }

        mainActivityVM.searchableLiveData.observe(viewLifecycleOwner) {
            if(it?.destination == SearchDestination.MAIN) {
                viewModel.searchQuery.set(it.query)
            }
        }
    }
}