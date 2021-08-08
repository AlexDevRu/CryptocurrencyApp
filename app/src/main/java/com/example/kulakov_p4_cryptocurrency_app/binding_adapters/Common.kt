package com.example.kulakov_p4_cryptocurrency_app.binding_adapters

import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.databinding.BindingAdapter
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

@BindingAdapter(value=["snackbarMessage", "snackbarBackground"])
fun View.showSnackbarMessage(message: String?, @ColorRes snackbarBackground: Int? = null) {
    if(!message.isNullOrEmpty()) {
        var snack = Snackbar.make(
            this,
            message,
            Snackbar.LENGTH_SHORT
        )

        if(snackbarBackground != null)
            snack = snack.setBackgroundTint(resources.getColor(snackbarBackground)).setTextColor(Color.WHITE)

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

/*@BindingAdapter(value = ["flashbarActivity", "flashbarTitle", "flashbarMessage",
    "flashbarBackground", "flashbarIcon"], requireAll = false)
fun View.showFlashbar(activity: FragmentActivity, title: String? = null, message: String? = null,
                      @ColorRes colorBg: Int? = null, @DrawableRes icon: Int? = null) {
    if(!message.isNullOrEmpty()) {
        var builder = Flashbar.Builder(activity)
            .gravity(Flashbar.Gravity.TOP)

        if(!title.isNullOrEmpty())
            builder = builder.title(title)

        if(!message.isNullOrEmpty())
            builder = builder.message(message)

        if(colorBg != null)
            builder = builder.backgroundColorRes(colorBg)

        if(icon != null)
            builder = builder.showIcon().icon(icon)

        builder.enterAnimation(
                FlashAnim.with(context)
                .animateBar()
                .duration(750)
                .alpha()
                .overshoot()
            )
            .exitAnimation(
                FlashAnim.with(context)
                .animateBar()
                .duration(400)
                .accelerateDecelerate()
            ).build().show()
    }
}*/