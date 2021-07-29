package com.example.kulakov_p4_cryptocurrency_app.view_models.items

import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.domain.models.Currency
import com.example.domain.models.QuoteItem

class CurrencyVM: BaseObservable() {

    @Bindable
    var currency: Currency? = null
        set(value) {
            field = value
            Log.w("asd", currency.toString())
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
}