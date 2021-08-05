package com.example.kulakov_p4_cryptocurrency_app.view_models

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.domain.aliases.CurrencyFlow
import com.example.domain.use_cases.GetCurrenciesUseCase
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
class MainVM @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase
): BaseVM() {

    val searchQuery = ObservableField<String>()

    val error = ObservableField<String>()
    val loading = ObservableBoolean(false)
    val isResultEmpty = ObservableBoolean(false)
    val listIsShown = ObservableBoolean(false)

    val scrollListToPosition = SingleLiveEvent<Int>()

    private var currentResult: CurrencyFlow? = null

    val sortFilterVM = SortFilterVM(::retry)

    private val _setCurrencies = MutableLiveData(true)
    val setCurrencies: LiveData<Boolean> = _setCurrencies

    private var searchJob: Job? = null

    init {
        searchQuery.addOnPropertyChangedCallback(PropertyChangedCallback { _, _ ->
            Log.w("asd", "searchQuery ${searchQuery.get()}")
            sortFilterVM.parameters.searchQuery = searchQuery.get().orEmpty()
            searchJob?.cancel()
            searchJob = viewModelScope.launch(Dispatchers.IO) {
                delay(1500)
                retry()
            }
        })
    }

    fun retry() {
        currentResult = null
        scrollListToPosition.postValue(0)
        _setCurrencies.postValue(true)
    }

    suspend fun getCurrencies(): CurrencyFlow {
        val lastResult = currentResult
        if (lastResult != null) {
            return lastResult
        }

        Log.e("asd", "sortFilterVM.parameters ${sortFilterVM.parameters}")

        val newResult = getCurrenciesUseCase.invoke(sortFilterVM.parameters).cachedIn(viewModelScope)

        currentResult = newResult

        return newResult
    }

    override fun onCleared() {
        super.onCleared()
        sortFilterVM.compositeDisposable.dispose()
    }
}