package com.example.kulakov_p4_cryptocurrency_app.view_models

import androidx.databinding.*
import com.example.domain.models.CurrencyParameters
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

class SortFilterVM(onChange: () -> Unit): BaseObservable() {
    private val types = listOf("all", "coins", "tokens")
    private val tags = listOf("all", "defi", "filesharing")
    private val sortTypes = listOf("name", "symbol", "date_added", "market_cap", "price")
    private val sortDirs = listOf("asc", "desc")

    val selectedTypePosition = ObservableInt(0)
    val selectedTagPosition = ObservableInt(0)

    val selectedSortTypePosition = ObservableInt(3)
    val selectedSortDirPosition = ObservableInt(1)

    val priceMin = BehaviorSubject.create<Float>()
    val priceMax = BehaviorSubject.create<Float>()

    val marketCapMin = BehaviorSubject.create<Float>()
    val marketCapMax = BehaviorSubject.create<Float>()


    val parameters = CurrencyParameters(
        types[selectedTypePosition.get()],
        types[selectedTagPosition.get()],
        sortType = sortTypes[selectedSortTypePosition.get()],
        sortDir = sortDirs[selectedSortDirPosition.get()]
    )

    val compositeDisposable = CompositeDisposable()

    init {
        selectedTypePosition.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if(parameters.type == types[selectedTypePosition.get()])
                    return
                parameters.type = types[selectedTypePosition.get()]
                onChange()
            }
        })

        selectedTagPosition.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if(parameters.tag == tags[selectedTagPosition.get()])
                    return
                parameters.tag = tags[selectedTagPosition.get()]
                onChange()
            }
        })

        compositeDisposable.add(priceMin.filter { parameters.priceMin != it }.subscribe {
            parameters.priceMin = it
            onChange()
        })

        compositeDisposable.add(priceMax.filter { parameters.priceMax != it }.subscribe {
            parameters.priceMax = it
            onChange()
        })

        compositeDisposable.add(marketCapMin.filter { parameters.marketCapMin != it }.subscribe {
            parameters.marketCapMin = it
            onChange()
        })

        compositeDisposable.add(marketCapMax.filter { parameters.marketCapMax != it }.subscribe {
            parameters.marketCapMax = it
            onChange()
        })

        selectedSortTypePosition.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if(parameters.sortType == sortTypes[selectedSortTypePosition.get()])
                    return
                parameters.sortType = sortTypes[selectedSortTypePosition.get()]
                onChange()
            }
        })

        selectedSortDirPosition.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if(parameters.sortDir == sortDirs[selectedSortDirPosition.get()])
                    return
                parameters.sortDir = sortDirs[selectedSortDirPosition.get()]
                onChange()
            }
        })
    }
}