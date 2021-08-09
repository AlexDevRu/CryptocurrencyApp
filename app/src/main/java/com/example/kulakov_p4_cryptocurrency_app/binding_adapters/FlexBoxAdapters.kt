package com.example.kulakov_p4_cryptocurrency_app.binding_adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.kulakov_p4_cryptocurrency_app.R
import com.google.android.flexbox.FlexboxLayout

@BindingAdapter("elements")
fun FlexboxLayout.elements(elements: List<String>? = null) {
    if(elements.isNullOrEmpty())
        return

    for(element in elements) {
        val textView = TextView(context)
        textView.text = element
        textView.background = context.resources.getDrawable(R.drawable.tag_bg)
        textView.setTextColor(resources.getColor(R.color.black))
        val layoutParams = FlexboxLayout.LayoutParams(
            FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.marginEnd = 12
        layoutParams.bottomMargin = 12
        textView.layoutParams = layoutParams
        addView(textView)
    }
}