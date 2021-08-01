package com.example.kulakov_p4_cryptocurrency_app.view_models.items

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.domain.models.Currency
import com.example.domain.models.QuoteItem

class CurrencyVM: BaseObservable() {

    @Bindable
    var currency: Currency? = null
        set(value) {
            field = value
            notifyChange()
        }

    @get:Bindable
    val thumbUrl: String
        get() = "https://s2.coinmarketcap.com/static/img/coins/64x64/${currency?.id}.png"

    @get:Bindable
    val quoteUSD: QuoteItem?
        get() = currency?.quote?.get("USD")

    @get:Bindable
    val price: String
        get() = "%.4f".format(quoteUSD!!.price)
    
    @get:Bindable
    val percent_change_1h: String
        get() = "%.4f".format(quoteUSD!!.percent_change_1h)

    @get:Bindable
    val percent_change_24h: String
        get() = "%.4f".format(quoteUSD!!.percent_change_24h)

    @get:Bindable
    val percent_change_30d: String
        get() = "%.4f".format(quoteUSD!!.percent_change_30d)


    @get:Bindable
    val priceDataList: List<Double>
        get() {
            if(quoteUSD == null) return emptyList()
            return listOf(
                calculatePrice(quoteUSD!!.percent_change_90d),
                calculatePrice(quoteUSD!!.percent_change_60d),
                calculatePrice(quoteUSD!!.percent_change_30d),
                calculatePrice(quoteUSD!!.percent_change_7d),
                calculatePrice(quoteUSD!!.percent_change_24h),
                calculatePrice(quoteUSD!!.percent_change_1h),
                quoteUSD!!.price
            )
        }

    private fun calculatePrice(percent: Double) = quoteUSD!!.price * (1 + percent / 100)
}