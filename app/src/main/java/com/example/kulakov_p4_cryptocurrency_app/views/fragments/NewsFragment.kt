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

        binding.newsList.adapter = adapter.withLoadStateHeaderAndFooter(
            CurrencyLoadStateAdapter { adapter.retry() },
            CurrencyLoadStateAdapter { adapter.retry() }
        )

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

        viewModel.scrollListToPosition.observe(viewLifecycleOwner, {
            if(it != null)
                binding.newsList.scrollToPosition(it)
        })
    }
}