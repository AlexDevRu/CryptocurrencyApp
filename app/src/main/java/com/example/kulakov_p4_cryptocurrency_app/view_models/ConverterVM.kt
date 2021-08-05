package com.example.kulakov_p4_cryptocurrency_app.view_models

import android.util.Log
import androidx.databinding.*
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Result
import com.example.domain.use_cases.GetLatestCurrencyUseCase
import com.example.kulakov_p4_cryptocurrency_app.view_models.base.BaseVM
import com.example.kulakov_p4_cryptocurrency_app.view_models.items.CurrencyVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.databinding.library.baseAdapters.BR
import com.example.kulakov_p4_cryptocurrency_app.utils.PropertyChangedCallback

@HiltViewModel
class ConverterVM @Inject constructor(
    private val getLatestCurrencyUseCase: GetLatestCurrencyUseCase
): BaseVM() {
    val fromCurrency = ObservableField<CurrencyVM>()
    val toCurrency = ObservableField<CurrencyVM>()

    val initialCurrencyLoading = ObservableBoolean(false)

    var firstSelected = false
    var secondSelected = false

    val fromValue = ObservableDouble(0.0)
    val toValue = ObservableDouble(0.0)


    var setFromCurrency = true


    @get:Bindable
    val graphDataList: List<List<Double>?>
        get() = listOf(fromCurrency.get()?.priceDataList, toCurrency.get()?.priceDataList)

    @get:Bindable
    val legendLabels: List<String?>
        get() = listOf(fromCurrency.get()?.currency?.name, toCurrency.get()?.currency?.name)

    init {
        fromCurrency.set(CurrencyVM())
        toCurrency.set(CurrencyVM())

        init()

        fromValue.addOnPropertyChangedCallback(PropertyChangedCallback { _, _ ->
            if(firstSelected && fromCurrency.get() != null && toCurrency.get() != null)
                toValue.set(fromCurrency.get()!!.quoteUSD!!.price * fromValue.get() / toCurrency.get()!!.quoteUSD!!.price)
        })

        toValue.addOnPropertyChangedCallback(PropertyChangedCallback { _, _ ->
            if(secondSelected && fromCurrency.get() != null && toCurrency.get() != null)
                fromValue.set(toCurrency.get()!!.quoteUSD!!.price * toValue.get() / fromCurrency.get()!!.quoteUSD!!.price)
        })
    }

    fun init() {
        initialCurrencyLoading.set(true)
        viewModelScope.launch(Dispatchers.IO) {
            val initialCurrencyResult = getLatestCurrencyUseCase.invoke()
            when(initialCurrencyResult) {
                is Result.Success -> {
                    fromCurrency.get()?.currency = initialCurrencyResult.value
                    toCurrency.get()?.currency = initialCurrencyResult.value
                    notifyPropertyChanged(BR.graphDataList)
                    notifyPropertyChanged(BR.legendLabels)
                }
                else -> {

                }
            }
            initialCurrencyLoading.set(false)
        }
    }
}