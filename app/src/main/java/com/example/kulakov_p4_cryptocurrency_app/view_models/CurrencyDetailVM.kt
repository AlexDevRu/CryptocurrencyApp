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
import com.example.domain.use_cases.UpdateCurrencyByIdUseCase

@HiltViewModel
class CurrencyDetailVM @Inject constructor(
    private val getCurrencyInfoUseCase: GetCurrencyInfoUseCase,
    private val updateCurrencyByIdUseCase: UpdateCurrencyByIdUseCase
): BaseVM() {

    val currencyVM = ObservableField<CurrencyVM>()
    val currencyMetadataVM = ObservableField<CurrencyMetadataVM>()

    val metadataLoading = ObservableBoolean(false)
    val metadataGetSuccessfull = ObservableBoolean(false)
    val currencyUpdated = ObservableBoolean(false)
    val metadataError = ObservableField<String>()
    val currencyError = ObservableField<String>()

    init {
        currencyMetadataVM.set(CurrencyMetadataVM())
        currencyVM.set(CurrencyVM())
        currencyUpdated.set(false)
    }

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
                    currencyMetadataVM.get()?.currencyMetadata = metadataResult.value
                    metadataError.set(null)
                }
                is Result.Failure -> metadataError.set(metadataResult.throwable.localizedMessage)
            }
            metadataLoading.set(false)
            metadataGetSuccessfull.set(metadataResult is Result.Success)
        }
    }

    fun updateCurrency() {
        val id = currencyVM.get()?.currency?.id ?: return

        currencyUpdated.set(true)
        viewModelScope.launch(Dispatchers.IO) {
            val updatedCurrencyResult = updateCurrencyByIdUseCase.invoke(id)
            when(updatedCurrencyResult) {
                is Result.Success -> {
                    currencyVM.get()?.currency = updatedCurrencyResult.value
                    currencyError.set(null)
                }
                is Result.Failure -> currencyError.set(updatedCurrencyResult.throwable.localizedMessage)
            }
            currencyUpdated.set(false)
        }
    }
}