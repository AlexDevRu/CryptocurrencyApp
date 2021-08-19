package com.example.kulakov_p4_cryptocurrency_app.view_models.items

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.domain.models.Currency
import com.example.domain.models.QuoteItem
import androidx.databinding.library.baseAdapters.BR
import java.util.*

class CurrencyVM: BaseObservable() {

    @Bindable
    var currency: Currency? = null
        set(value) {
            field = value
            notifyChange()
        }

    @Bindable
    var favorite: Boolean = currency?.addedToFavorite != null
        get() = currency?.addedToFavorite != null
        set(value) {
            field = value
            currency?.addedToFavorite = if(field) Date() else null
            notifyPropertyChanged(BR.favorite)
        }

    @get:Bindable
    val thumbUrl: String
        get() = "https://s2.coinmarketcap.com/static/img/coins/64x64/${currency?.id}.png"

    @get:Bindable
    val quoteUSD: QuoteItem?
        get() = currency?.quote?.get("USD")

    @get:Bindable
    val titleWithSymbol: String
        get() = "${currency?.name} | ${currency?.symbol}"

    @get:Bindable
    val priceDataList: List<Double>
        get() {
            if(quoteUSD == null) return emptyList()
            return listOf(
                calculatePrice(quoteUSD!!.percentChange90d),
                calculatePrice(quoteUSD!!.percentChange60d),
                calculatePrice(quoteUSD!!.percentChange30d),
                calculatePrice(quoteUSD!!.percentChange7d),
                calculatePrice(quoteUSD!!.percentChange24h),
                calculatePrice(quoteUSD!!.percentChange1h),
                quoteUSD!!.price
            )
        }

    private fun calculatePrice(percent: Double) = quoteUSD!!.price / (1 + percent / 100)
}