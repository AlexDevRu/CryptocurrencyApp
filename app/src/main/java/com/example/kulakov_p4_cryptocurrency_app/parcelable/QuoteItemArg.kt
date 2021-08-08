package com.example.kulakov_p4_cryptocurrency_app.parcelable

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuoteItemArg(
    val marketCap: Double,
    val percentChange1h: Double,
    val percentChange24h: Double,
    val percentChange7d: Double,
    val percentChange30d: Double,
    val percentChange60d: Double,
    val percentChange90d: Double,
    val price: Double,
    val volume24h: Double
): Parcelable