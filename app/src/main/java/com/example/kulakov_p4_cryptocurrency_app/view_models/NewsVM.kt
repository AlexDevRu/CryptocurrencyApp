package com.example.kulakov_p4_cryptocurrency_app.view_models

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.domain.aliases.ArticleFlow
import com.example.domain.use_cases.GetNewsUseCase
import com.example.kulakov_p4_cryptocurrency_app.events.SingleLiveEvent
import com.example.kulakov_p4_cryptocurrency_app.utils.PropertyChangedCallback
import com.example.kulakov_p4_cryptocurrency_app.view_models.base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsVM @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
): BaseVM() {

    companion object {
        private const val DEFAULT_QUERY = "cryptocurrency"
    }

    val searchQuery = ObservableField<String>()
    var currentQuery: String? = null

    val error = ObservableField<String>()
    val loading = ObservableBoolean(false)
    val isResultEmpty = ObservableBoolean(false)

    val scrollListToPosition = SingleLiveEvent<Int>()

    private val _setNews = MutableLiveData(true)
    val setNews: LiveData<Boolean> = _setNews

    private var currentResult: ArticleFlow? = null

    private var searchJob: Job? = null

    init {
        searchQuery.addOnPropertyChangedCallback(PropertyChangedCallback { _, _ ->
            Log.w("asd", "searchQuery ${searchQuery.get()}")
            searchJob?.cancel()
            searchJob = viewModelScope.launch(Dispatchers.IO) {
                delay(1500)
                retry()
            }
        })
    }

    fun retry() {
        scrollListToPosition.postValue(0)
        currentResult = null
        _setNews.postValue(true)
    }

    suspend fun getCurrencies(): ArticleFlow {
        val lastResult = currentResult
        if (lastResult != null) {
            return lastResult
        }

        val query = if(searchQuery.get().isNullOrEmpty()) DEFAULT_QUERY else searchQuery.get()!!

        val newResult = getNewsUseCase.invoke(query).cachedIn(viewModelScope)

        currentResult = newResult

        return newResult
    }
}