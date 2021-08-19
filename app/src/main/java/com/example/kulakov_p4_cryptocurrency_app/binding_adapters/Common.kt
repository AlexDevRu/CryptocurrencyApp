package com.example.kulakov_p4_cryptocurrency_app.binding_adapters

import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import com.example.kulakov_p4_cryptocurrency_app.R
import com.google.android.material.snackbar.Snackbar


@BindingAdapter("android:visibility")
fun convertBooleanToVisibility(view: View, visible: Any?) {
    if(visible is Boolean) {
        view.visibility = if(visible) View.VISIBLE else View.GONE
    } else if(visible is Int && (visible == View.VISIBLE || visible == View.INVISIBLE || visible == View.GONE)) {
        view.visibility = visible
    }
}

@BindingConversion
fun convToBoolean(value: Boolean): Int {
    return if(value) View.VISIBLE else View.GONE
}

@BindingAdapter(value=["snackbarMessage", "snackbarBackground"])
fun View.showSnackbarMessage(message: String?, @ColorRes snackbarBackground: Int? = null) {
    if(!message.isNullOrEmpty()) {
        var snack = Snackbar.make(
            this,
            message,
            Snackbar.LENGTH_SHORT
        )

        if(snackbarBackground != null)
            snack = snack.setBackgroundTint(ContextCompat.getColor(context, snackbarBackground)).setTextColor(Color.WHITE)

        val view = snack.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.CENTER_HORIZONTAL or Gravity.TOP

        val tv = TypedValue()
        var actionBarHeight = 0
        if (context.theme.resolveAttribute(R.attr.actionBarSize, tv, true)) {
            actionBarHeight =
                TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
        }

        params.setMargins(0, actionBarHeight, 0, 0)

        view.layoutParams = params
        snack.show()
    }
}
