package com.example.kulakov_p4_cryptocurrency_app.parcelable

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class CurrencyArg(
    val id: Int,
    val name: String?,
    val symbol: String?,
    val last_updated: Date = Date(),
    val quote: Map<String, QuoteItemArg>
): Parcelable