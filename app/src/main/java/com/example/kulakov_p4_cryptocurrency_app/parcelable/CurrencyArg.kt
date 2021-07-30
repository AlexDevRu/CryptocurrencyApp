package com.example.kulakov_p4_cryptocurrency_app.parcelable

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrencyArg(
    val id: Int,
    val name: String?,
    val symbol: String?,
    val quote: Map<String, QuoteItemArg>
): Parcelable