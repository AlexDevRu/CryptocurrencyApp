package com.example.kulakov_p4_cryptocurrency_app.parcelable

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class CurrencyArg(
    val id: Int,
    val name: String?,
    val symbol: String?,
    val lastUpdated: Date = Date(),
    val cmcRank: Int = 0,
    val circulatingSupply: Double = 0.0,
    val dateAdded: Date = Date(),
    val maxSupply: Double = 0.0,
    val marketPairs: Int = 0,
    val tags: List<String>? = null,
    val totalSupply: Double = 0.0,
    val quote: Map<String, QuoteItemArg>
): Parcelable