package com.example.kulakov_p4_cryptocurrency_app.binding_adapters

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.kulakov_p4_cryptocurrency_app.R
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter(value = ["usd", "prefix"], requireAll = false)
fun TextView.usd(money: Double, prefix: String? = null) {
    val formattedUsd = "$${"%.4f".format(money)}"
    val formattedText = if(prefix != null) prefix + formattedUsd else formattedUsd
    text = formattedText
}

@BindingAdapter("percents")
fun customizePercents(textView: TextView, percents: Double) {
    val formatted = "${"%.4f".format(percents)}%"
    textView.text = formatted
    if(percents.compareTo(0.0) > 0) {
        textView.background = ContextCompat.getDrawable(textView.context, R.drawable.positive_percent_bg)
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_arrow_drop_up_24, 0)
    } else {
        textView.background = ContextCompat.getDrawable(textView.context, R.drawable.negative_percent_bg)
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_arrow_drop_down_24, 0)
    }
}

@BindingAdapter("date")
fun bindDate(textView: TextView, date: Date) {
    textView.text = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(date)
}
