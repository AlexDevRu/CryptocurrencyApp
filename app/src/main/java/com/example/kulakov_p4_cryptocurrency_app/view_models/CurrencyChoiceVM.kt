package com.example.kulakov_p4_cryptocurrency_app.view_models

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.domain.aliases.CurrencyFlow
import com.example.domain.models.CurrencyParameters
import com.example.domain.use_cases.GetCurrenciesUseCase
import com.example.kulakov_p4_cryptocurrency_app.events.SingleLiveEvent
import com.example.kulakov_p4_cryptocurrency_app.view_models.base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class CurrencyChoiceVM @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase
): BaseVM() {
    private val compositeDisposable = CompositeDisposable()

    val searchQuery = BehaviorSubject.createDefault("")

    val error = ObservableField<String>()
    val loading = ObservableBoolean(false)
    val isResultEmpty = ObservableBoolean(false)
    val listIsShown = ObservableBoolean(false)

    val scrollListToPosition = SingleLiveEvent<Int>()

    private var currentResult: CurrencyFlow? = null

    private val _setCurrencies = MutableLiveData(true)
    val setCurrencies: LiveData<Boolean> = _setCurrencies

    val parameters = CurrencyParameters()

    init {
        compositeDisposable.add(searchQuery
            .debounce(1500, TimeUnit.MILLISECONDS)
            .subscribe {
                parameters.searchQuery = it
                retry()
            }
        )
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

        val newResult = getCurrenciesUseCase.invoke(parameters).cachedIn(viewModelScope)

        currentResult = newResult

        return newResult
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}