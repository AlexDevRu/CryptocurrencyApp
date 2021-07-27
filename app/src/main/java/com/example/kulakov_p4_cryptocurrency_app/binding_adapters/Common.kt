package com.example.kulakov_p4_cryptocurrency_app.binding_adapters

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import com.google.android.material.snackbar.Snackbar

@BindingConversion
fun convertBooleanToVisibility(visible: Boolean): Int {
    if(visible) return View.VISIBLE
    return View.GONE
}

@BindingAdapter("showSnackbarMessage")
fun bindSnackbar(view: View, message: String?) {
    if(!message.isNullOrEmpty()) {
        Snackbar.make(view,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}