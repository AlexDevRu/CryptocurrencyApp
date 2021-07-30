package com.example.kulakov_p4_cryptocurrency_app.parcelable

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuoteItemArg(
    val last_updated: String,
    val market_cap: Double,
    val percent_change_1h: Double,
    val percent_change_24h: Double,
    val percent_change_30d: Double,
    val percent_change_60d: Double,
    val percent_change_7d: Double,
    val percent_change_90d: Double,
    val price: Double,
    val volume_24h: Double
): Parcelable