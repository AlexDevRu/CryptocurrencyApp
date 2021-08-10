package com.example.kulakov_p4_cryptocurrency_app.view_models

import android.util.Log
import androidx.databinding.*
import com.example.domain.models.CurrencyParameters
import com.example.kulakov_p4_cryptocurrency_app.utils.PropertyChangedCallback

class SortFilterVM(onChange: () -> Unit): BaseObservable() {
    private val types = listOf("all", "coins", "tokens")
    private val tags = listOf("all", "defi", "filesharing")
    private val sortTypes = listOf("name", "symbol", "date_added", "market_cap", "price")
    private val sortDirs = listOf("asc", "desc")

    val selectedTypePosition = ObservableInt(0)
    val selectedTagPosition = ObservableInt(0)

    val selectedSortTypePosition = ObservableInt(3)
    val selectedSortDirPosition = ObservableInt(1)

    val priceMin = ObservableFloat()
    val priceMax = ObservableFloat()

    val marketCapMin = ObservableFloat()
    val marketCapMax = ObservableFloat()

    val parameters = CurrencyParameters(
        types[selectedTypePosition.get()],
        types[selectedTagPosition.get()],
        sortType = sortTypes[selectedSortTypePosition.get()],
        sortDir = sortDirs[selectedSortDirPosition.get()]
    )

    init {
        selectedTypePosition.addOnPropertyChangedCallback(PropertyChangedCallback {
            if(parameters.type == types[selectedTypePosition.get()])
                return@PropertyChangedCallback
            parameters.type = types[selectedTypePosition.get()]
            onChange()
        })

        selectedTagPosition.addOnPropertyChangedCallback(PropertyChangedCallback {
            if(parameters.tag == tags[selectedTagPosition.get()])
                return@PropertyChangedCallback
            parameters.tag = tags[selectedTagPosition.get()]
            onChange()
        })

        priceMin.addOnPropertyChangedCallback(PropertyChangedCallback {
            Log.d("asd", "price min ${priceMin.get()}")
            if(parameters.priceMin == priceMin.get().toDouble())
                return@PropertyChangedCallback
            parameters.priceMin = priceMin.get().toDouble()
            onChange()
        })

        priceMax.addOnPropertyChangedCallback(PropertyChangedCallback {
            Log.d("asd", "price max ${priceMax.get()}")
            if(parameters.priceMax == priceMax.get().toDouble())
                return@PropertyChangedCallback
            parameters.priceMax = priceMax.get().toDouble()
            onChange()
        })

        marketCapMin.addOnPropertyChangedCallback(PropertyChangedCallback {
            Log.d("asd", "market min ${marketCapMin.get()}")
            if(parameters.marketCapMin == marketCapMin.get().toDouble())
                return@PropertyChangedCallback
            parameters.marketCapMin = marketCapMin.get().toDouble()
            onChange()
        })

        marketCapMax.addOnPropertyChangedCallback(PropertyChangedCallback {
            Log.d("asd", "market max ${marketCapMax.get()}")
            if(parameters.marketCapMax == marketCapMax.get().toDouble())
                return@PropertyChangedCallback
            parameters.marketCapMax = marketCapMax.get().toDouble()
            onChange()
        })

        selectedSortTypePosition.addOnPropertyChangedCallback(PropertyChangedCallback {
            if(parameters.sortType == sortTypes[selectedSortTypePosition.get()])
                return@PropertyChangedCallback
            parameters.sortType = sortTypes[selectedSortTypePosition.get()]
            onChange()
        })

        selectedSortDirPosition.addOnPropertyChangedCallback(PropertyChangedCallback {
            if(parameters.sortDir == sortDirs[selectedSortDirPosition.get()])
                return@PropertyChangedCallback
            parameters.sortDir = sortDirs[selectedSortDirPosition.get()]
            onChange()
        })
    }
}