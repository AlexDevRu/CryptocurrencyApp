package com.example.kulakov_p4_cryptocurrency_app.view_models

import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.example.domain.models.CurrencyParameters
import com.example.domain.use_cases.GetCurrenciesUseCase
import com.example.domain.use_cases.GetLatestCurrencyUseCase
import com.example.kulakov_p4_cryptocurrency_app.view_models.base.BaseVM
import com.example.kulakov_p4_cryptocurrency_app.view_models.items.CurrencyVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.domain.common.Result
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import androidx.databinding.library.baseAdapters.BR

@HiltViewModel
class ConverterVM @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val getLatestCurrencyUseCase: GetLatestCurrencyUseCase

): BaseVM() {
    val fromCurrency = ObservableField<CurrencyVM>()
    val toCurrency = ObservableField<CurrencyVM>()

    //val fromValue = BehaviorSubject.createDefault(0.0)
    //val toValue = BehaviorSubject.createDefault(0.0)

    @Bindable
    var fromValue = 0.0
        set(value) {
            field = value
            if(selectedItem == SelectedItem.FIRST)
                toValue = value * fromCurrency.get()?.quoteUSD?.price!! / toCurrency.get()?.quoteUSD?.price!!
            notifyPropertyChanged(BR.fromValue)
        }

    @Bindable
    var toValue = 0.0
        set(value) {
            field = value
            if(selectedItem == SelectedItem.SECOND)
                fromValue = value * toCurrency.get()?.quoteUSD?.price!! / fromCurrency.get()?.quoteUSD?.price!!
            notifyPropertyChanged(BR.toValue)
        }


    val initialCurrencyLoading = ObservableBoolean(false)

    val compositeDisposable = CompositeDisposable()

    enum class SelectedItem {
        FIRST, SECOND, NONE
    }

    fun setFirstSelected() {
        selectedItem = SelectedItem.FIRST
    }
    fun setSecondSelected() {
        selectedItem = SelectedItem.SECOND
    }

    var selectedItem = SelectedItem.NONE

    init {
        init()

        /*compositeDisposable.add(fromValue.filter {
            Log.w("asd", "it $it, fromValue ${fromValue.value}")
            it != fromValue.value
        }.subscribe {
            val newValue = it * fromCurrency.get()?.quoteUSD?.price!! / toCurrency.get()?.quoteUSD?.price!!
            Log.w("asd", "newValue $newValue")
            if(selectedItem == SelectedItem.FIRST) toValue.onNext(newValue)
        })

        compositeDisposable.add(toValue.filter { it != toValue.value }.subscribe {
            val newValue = it * toCurrency.get()?.quoteUSD?.price!! / fromCurrency.get()?.quoteUSD?.price!!
            if(selectedItem == SelectedItem.SECOND) fromValue.onNext(newValue)
        })*/
    }

    fun init() {
        initialCurrencyLoading.set(true)
        viewModelScope.launch(Dispatchers.IO) {
            val initialCurrencyResult = getLatestCurrencyUseCase.invoke()
            when(initialCurrencyResult) {
                is Result.Success -> {
                    val observableFrom = CurrencyVM()
                    val observableTo = CurrencyVM()
                    observableFrom.currency = initialCurrencyResult.value
                    observableTo.currency = initialCurrencyResult.value
                    fromCurrency.set(observableFrom)
                    toCurrency.set(observableTo)
                }
                else -> {

                }
            }
            initialCurrencyLoading.set(false)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}