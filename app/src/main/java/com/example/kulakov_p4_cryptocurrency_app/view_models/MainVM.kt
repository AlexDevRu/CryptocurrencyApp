package com.example.kulakov_p4_cryptocurrency_app.view_models

import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.domain.aliases.CurrencyFlow
import com.example.domain.models.CurrencyParameters
import com.example.domain.repositories.remote.ICoinMarketCapRespository
import com.example.kulakov_p4_cryptocurrency_app.view_models.base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val repository: ICoinMarketCapRespository
): BaseVM() {

    val error = ObservableField<String>()
    val loading = ObservableBoolean(false)

    private var currentResult: CurrencyFlow? = null

    val sortFilterVM = SortFilterVM(::handler)

    private val _setCurrencies = MutableLiveData(true)
    val setCurrencies: LiveData<Boolean> = _setCurrencies

    init {
        //sortFilterVM.selectedTypePosition.addOnPropertyChangedCallback(SortFilterPropertyChangedCallback())
        //sortFilterVM.selectedTagPosition.addOnPropertyChangedCallback(SortFilterPropertyChangedCallback())
    }

    inner class SortFilterPropertyChangedCallback: Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            currentResult = null
            _setCurrencies.postValue(true)
        }
    }

    private fun handler() {
        currentResult = null
        _setCurrencies.postValue(true)
    }

    suspend fun getCurrencies(): CurrencyFlow {
        val lastResult = currentResult
        if (lastResult != null) {
            return lastResult
        }

        val newResult = repository.getAllCurrencies(sortFilterVM.parameters).cachedIn(viewModelScope)

        currentResult = newResult

        return newResult
    }

    override fun onCleared() {
        super.onCleared()
        sortFilterVM.compositeDisposable.dispose()
    }
}