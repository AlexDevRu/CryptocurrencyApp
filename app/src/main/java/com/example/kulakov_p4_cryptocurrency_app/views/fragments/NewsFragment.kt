package com.example.kulakov_p4_cryptocurrency_app.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.kulakov_p4_cryptocurrency_app.R
import com.example.kulakov_p4_cryptocurrency_app.adapters.CurrencyLoadStateAdapter
import com.example.kulakov_p4_cryptocurrency_app.adapters.NewsAdapter
import com.example.kulakov_p4_cryptocurrency_app.databinding.FragmentNewsBinding
import com.example.kulakov_p4_cryptocurrency_app.utils.SearchDestination
import com.example.kulakov_p4_cryptocurrency_app.view_models.NewsVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException

@AndroidEntryPoint
class NewsFragment: BaseFragment<FragmentNewsBinding>
    (R.layout.fragment_news) {

    private val viewModel: NewsVM by viewModels()

    private var getNewsJob: Job? = null

    private val adapter = NewsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        val header = CurrencyLoadStateAdapter { adapter.retry() }
        val footer = CurrencyLoadStateAdapter { adapter.retry() }

        adapter.addLoadStateListener { state ->
            val isListEmpty = state.refresh is LoadState.NotLoading && adapter.itemCount == 0
            viewModel.listVM.isResultEmpty.set(isListEmpty)

            viewModel.listVM.loading.set(state.refresh is LoadState.Loading || state.mediator?.refresh is LoadState.Loading)

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
                viewModel.listVM.error.set(error)
            }
        }

        binding.newsList.adapter = adapter.withLoadStateHeaderAndFooter(header, footer)

        initToolbar(binding.toolbar)

        observe()
    }

    private fun observe() {
        viewModel.setNews.observe(viewLifecycleOwner) {
            getNewsJob?.cancel()
            getNewsJob = lifecycleScope.launch(Dispatchers.IO) {
                viewModel.getCurrencies().collectLatest {
                    adapter.submitData(it)
                }
            }
        }

        mainActivityVM.searchableLiveData.observe(viewLifecycleOwner) {
            if(it?.destination == SearchDestination.NEWS) {
                viewModel.searchQuery.set(it.query)
            }
        }
    }
}