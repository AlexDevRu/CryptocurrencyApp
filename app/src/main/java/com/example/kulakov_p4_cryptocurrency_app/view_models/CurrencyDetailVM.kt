package com.example.kulakov_p4_cryptocurrency_app.view_models

import androidx.databinding.ObservableField
import com.example.domain.models.Currency
import com.example.domain.use_cases.GetCurrenciesUseCase
import com.example.kulakov_p4_cryptocurrency_app.view_models.base.BaseVM
import com.example.kulakov_p4_cryptocurrency_app.view_models.items.CurrencyVM
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrencyDetailVM @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase
): BaseVM() {

    val currencyVM = ObservableField<CurrencyVM>()

    fun init(item: Currency) {
        val observable = CurrencyVM()
        observable.currency = item
        currencyVM.set(observable)
    }
}