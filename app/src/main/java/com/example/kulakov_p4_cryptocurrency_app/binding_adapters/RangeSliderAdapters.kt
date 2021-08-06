package com.example.kulakov_p4_cryptocurrency_app.binding_adapters

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.slider.RangeSlider

@BindingAdapter("startValue")
fun setRangeStartValue(rangeSlider: RangeSlider, startValue: Float) {
    if (rangeSlider.values[0] == startValue) return
    rangeSlider.values[0] = startValue
}

@BindingAdapter("endValue")
fun setRangeEndValue(rangeSlider: RangeSlider, endValue: Float) {
    if (rangeSlider.values[1] == endValue) return
    rangeSlider.values[1] = endValue
}

@InverseBindingAdapter(attribute = "startValue", event = "startValueAttrChanged")
fun getStartValue(rangeSlider: RangeSlider): Float {
    return rangeSlider.values[0]
}

@InverseBindingAdapter(attribute = "endValue", event = "endValueAttrChanged")
fun getEndValue(rangeSlider: RangeSlider): Float {
    return rangeSlider.values[1]
}

@BindingAdapter("startValueAttrChanged")
fun setStartValueAttrChanged(rangeSlider: RangeSlider, startValueAttrChanged: InverseBindingListener) {
    addValueChangeListener(rangeSlider, startValueAttrChanged)
}

@BindingAdapter("endValueAttrChanged")
fun setEndValueAttrChanged(rangeSlider: RangeSlider, endValueAttrChanged: InverseBindingListener) {
    addValueChangeListener(rangeSlider, endValueAttrChanged)
}

fun addValueChangeListener(rangeSlider: RangeSlider, valueAttrChanged: InverseBindingListener) {
    rangeSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener{
        override fun onStartTrackingTouch(slider: RangeSlider) {
        }

        override fun onStopTrackingTouch(slider: RangeSlider) {
            valueAttrChanged.onChange()
        }
    })
}