package com.example.kulakov_p4_cryptocurrency_app.view_models

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Currency
import com.example.domain.use_cases.GetFavoriteCurrenciesUseCase
import com.example.kulakov_p4_cryptocurrency_app.view_models.base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.domain.common.Result
import com.example.domain.use_cases.firebase.DeleteFavoriteCurrencyUseCase
import com.example.kulakov_p4_cryptocurrency_app.utils.PropertyChangedCallback
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

@HiltViewModel
class FavoriteVM @Inject constructor(
    private val getFavoriteCurrenciesUseCase: GetFavoriteCurrenciesUseCase,
    private val deleteFavoriteCurrencyUseCase: DeleteFavoriteCurrencyUseCase
): BaseVM() {

    val searchQuery = ObservableField<String>()

    private val _setFavoriteCurrencies = MutableLiveData(true)
    val setFavoriteCurrencies: LiveData<Boolean> = _setFavoriteCurrencies

    val listVM = RecyclerViewVM()

    var favoriteList = mutableListOf<Currency>()

    private var favoriteJob: Job? = null
    private var deleteFromFavoriteJob: Job? = null

    private var searchJob: Job? = null

    init {
        retry()

        searchQuery.addOnPropertyChangedCallback(PropertyChangedCallback {
            Log.w("asd", "searchQuery ${searchQuery.get()}")
            searchJob?.cancel()
            searchJob = viewModelScope.launch(Dispatchers.IO) {
                delay(1500)
                retry()
            }
        })
    }

    fun retry() {
        favoriteJob?.cancel()
        listVM.loading.set(true)
        favoriteJob = viewModelScope.launch(Dispatchers.IO) {
            val result = getFavoriteCurrenciesUseCase.invoke(searchQuery.get().orEmpty())
            when(result) {
                is Result.Success -> {
                    listVM.error.set(null)
                    favoriteList = result.value.toMutableList()
                    listVM.isResultEmpty.set(favoriteList.isEmpty())
                    _setFavoriteCurrencies.postValue(true)
                }
                is Result.Failure -> listVM.error.set(result.throwable.message)
            }
            listVM.loading.set(false)
        }
    }

    fun deleteFromFavorite(currency: Currency) {
        deleteFromFavoriteJob?.cancel()
        deleteFromFavoriteJob = viewModelScope.launch(Dispatchers.IO) {
            deleteFavoriteCurrencyUseCase.invoke(currency)
        }
        favoriteList.remove(currency)
        listVM.isResultEmpty.set(favoriteList.isEmpty())
    }
}