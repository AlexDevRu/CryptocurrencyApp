package com.example.kulakov_p4_cryptocurrency_app.view_models

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.domain.aliases.ArticleFlow
import com.example.domain.use_cases.GetNewsUseCase
import com.example.kulakov_p4_cryptocurrency_app.events.SingleLiveEvent
import com.example.kulakov_p4_cryptocurrency_app.view_models.base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class NewsVM @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
): BaseVM() {

    companion object {
        private const val DEFAULT_QUERY = "cryptocurrency"
    }

    val compositeDisposable = CompositeDisposable()
    val searchQuery = BehaviorSubject.createDefault("")
    var currentQuery = searchQuery.value.orEmpty()

    val error = ObservableField<String>()
    val loading = ObservableBoolean(false)
    val isResultEmpty = ObservableBoolean(false)

    val scrollListToPosition = SingleLiveEvent<Int>()

    private val _setNews = MutableLiveData(true)
    val setNews: LiveData<Boolean> = _setNews

    private var currentResult: ArticleFlow? = null

    init {
        compositeDisposable.add(searchQuery
            .filter { it != currentQuery }
            .debounce(1500, TimeUnit.MILLISECONDS)
            .subscribe {
                currentQuery = it
                retry()
            }
        )
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

        val query = if(searchQuery.value?.isEmpty() == true) DEFAULT_QUERY else searchQuery.value!!

        val newResult = getNewsUseCase.invoke(query).cachedIn(viewModelScope)

        currentResult = newResult

        return newResult
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}