package com.example.kulakov_p4_cryptocurrency_app.ui_models

import androidx.annotation.StringRes

data class DataTableItem(
    @StringRes val title: Int,
    val value: Any?,
    val isMoney: Boolean = false
)