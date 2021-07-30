package com.example.kulakov_p4_cryptocurrency_app.view_models

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Currency
import com.example.domain.use_cases.GetCurrencyInfoUseCase
import com.example.kulakov_p4_cryptocurrency_app.view_models.base.BaseVM
import com.example.kulakov_p4_cryptocurrency_app.view_models.items.CurrencyMetadataVM
import com.example.kulakov_p4_cryptocurrency_app.view_models.items.CurrencyVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.domain.common.Result

@HiltViewModel
class CurrencyDetailVM @Inject constructor(
    private val getCurrencyInfoUseCase: GetCurrencyInfoUseCase
): BaseVM() {

    val currencyVM = ObservableField<CurrencyVM>()
    val currencyMetadataVM = ObservableField<CurrencyMetadataVM>()

    val metadataLoading = ObservableBoolean(false)
    val metadataGetSuccessfull = ObservableBoolean(false)
    val metadataError = ObservableField<String>()

    fun init(item: Currency) {
        val observable = CurrencyVM()
        observable.currency = item
        currencyVM.set(observable)
        getMetadata()
    }

    fun getMetadata() {
        metadataGetSuccessfull.set(false)
        metadataLoading.set(true)
        viewModelScope.launch(Dispatchers.IO) {
            val metadataResult = getCurrencyInfoUseCase.invoke(currencyVM.get()!!.currency!!.id)
            when(metadataResult) {
                is Result.Success -> {
                    val observable = CurrencyMetadataVM()
                    observable.currencyMetadata = metadataResult.value
                    currencyMetadataVM.set(observable)
                    metadataError.set(null)
                }
                is Result.Failure -> metadataError.set(metadataResult.throwable.localizedMessage)
            }
            metadataLoading.set(false)
            metadataGetSuccessfull.set(metadataResult is Result.Success)
        }
    }
}