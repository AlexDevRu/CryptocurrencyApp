package com.example.kulakov_p4_cryptocurrency_app.binding_adapters

import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.snackbar.Snackbar

@BindingAdapter("android:visibility")
fun convertBooleanToVisibility(view: View, visible: Any?) {
    if(visible is Boolean) {
        view.visibility = if(visible) View.VISIBLE else View.GONE
    } else if(visible is Int && (visible == View.VISIBLE || visible == View.INVISIBLE || visible == View.GONE)) {
        view.visibility = visible as Int
    }
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