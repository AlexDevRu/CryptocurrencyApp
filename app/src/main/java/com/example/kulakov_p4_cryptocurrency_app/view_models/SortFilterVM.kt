package com.example.kulakov_p4_cryptocurrency_app.view_models

import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt

class SortFilterVM: BaseObservable() {
    private val types = listOf("all", "coins", "tokens")

    val selectedCurrencyPosition = ObservableInt(0)
    var type = ObservableField(types[selectedCurrencyPosition.get()])

    init {
        selectedCurrencyPosition.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                type.set(types[selectedCurrencyPosition.get()])
            }
        })
    }
}