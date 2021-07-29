package com.example.kulakov_p4_cryptocurrency_app.binding_adapters

import androidx.databinding.BindingAdapter
import com.google.android.material.slider.RangeSlider
import io.reactivex.subjects.BehaviorSubject

@BindingAdapter(value = ["startValue", "endValue"], requireAll = true)
fun rxRangeSliderStartValue(rangeSlider: RangeSlider, startSubject: BehaviorSubject<Float>, endSubject: BehaviorSubject<Float>) {

    startSubject.onNext(rangeSlider.values[0])
    endSubject.onNext(rangeSlider.values[1])

    // Text changes
    rangeSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener{
        override fun onStartTrackingTouch(slider: RangeSlider) {
        }

        override fun onStopTrackingTouch(slider: RangeSlider) {
            startSubject.onNext(rangeSlider.values[0])
            endSubject.onNext(rangeSlider.values[1])
        }
    })
}