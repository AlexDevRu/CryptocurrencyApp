package com.example.kulakov_p4_cryptocurrency_app.binding_adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.kulakov_p4_cryptocurrency_app.R

@BindingAdapter("usd")
fun customizeMoney(textView: TextView, money: Double) {
    val formatted = "$${"%.4f".format(money)}"
    textView.text = formatted
}

@BindingAdapter("percents")
fun customizePercents(textView: TextView, percents: Double) {
    val formatted = "${"%.4f".format(percents)}%"
    textView.text = formatted
    if(percents.compareTo(0.0) > 0) {
        textView.background = textView.resources.getDrawable(R.drawable.positive_percent_bg)
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_arrow_drop_up_24, 0)
    } else {
        textView.background = textView.resources.getDrawable(R.drawable.negative_percent_bg)
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_arrow_drop_down_24, 0)
    }
}