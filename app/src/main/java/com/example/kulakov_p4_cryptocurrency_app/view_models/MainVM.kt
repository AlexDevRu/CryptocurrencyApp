package com.example.kulakov_p4_cryptocurrency_app.view_models

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.domain.aliases.CurrencyFlow
import com.example.domain.models.Currency
import com.example.domain.use_cases.GetCurrenciesUseCase
import com.example.domain.use_cases.SearchCurrencyByQueryUseCase
import com.example.domain.use_cases.firebase.DeleteFavoriteCurrencyUseCase
import com.example.domain.use_cases.firebase.SaveFavoriteCurrencyUseCase
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
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val searchCurrencyByQueryUseCase: SearchCurrencyByQueryUseCase,
    private val saveFavoriteCurrencyUseCase: SaveFavoriteCurrencyUseCase,
    private val deleteFavoriteCurrencyUseCase: DeleteFavoriteCurrencyUseCase
): BaseVM() {

    val searchQuery = ObservableField<String>()

    val listVM = RecyclerViewVM()
    val isOnline = ObservableBoolean(false)

    private var currentResult: CurrencyFlow? = null

    val sortFilterVM = SortFilterVM(::retry)

    private val _setCurrencies = MutableLiveData(true)
    val setCurrencies: LiveData<Boolean> = _setCurrencies

    private var searchJob: Job? = null
    private var updateFavoriteJob: Job? = null

    init {
        searchQuery.addOnPropertyChangedCallback(PropertyChangedCallback {
            if(sortFilterVM.parameters.searchQuery == searchQuery.get().orEmpty())
                return@PropertyChangedCallback

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
        _setCurrencies.postValue(true)
    }

    suspend fun getCurrencies(): CurrencyFlow {
        val lastResult = currentResult
        if (lastResult != null) {
            return lastResult
        }

        Log.e("asd", "sortFilterVM.parameters ${sortFilterVM.parameters}")

        val newResult = if(searchQuery.get().isNullOrEmpty())
            getCurrenciesUseCase.invoke(sortFilterVM.parameters).cachedIn(viewModelScope)
        else
            searchCurrencyByQueryUseCase.invoke(sortFilterVM.parameters).cachedIn(viewModelScope)

        currentResult = newResult

        return newResult
    }

    fun updateFavorite(currency: Currency) {
        updateFavoriteJob?.cancel()
        updateFavoriteJob = viewModelScope.launch(Dispatchers.IO) {
            Log.w("asd", "currency updated ${currency.addedToFavorite}")
            if(currency.addedToFavorite != null)
                saveFavoriteCurrencyUseCase.invoke(currency)
            else
                deleteFavoriteCurrencyUseCase.invoke(currency)
        }
    }
}