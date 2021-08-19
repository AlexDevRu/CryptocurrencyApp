package com.example.kulakov_p4_cryptocurrency_app.utils

import java.io.Serializable

enum class SearchDestination : Serializable {
    MAIN, CHOICE, NEWS, FAVORITE
}

data class SearchableResult(
    val query: String,
    val destination: SearchDestination
)