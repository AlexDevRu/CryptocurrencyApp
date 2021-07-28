package com.example.kulakov_p4_cryptocurrency_app.binding_adapters

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener


@BindingAdapter("entries")
fun bindAdapterToSpinner(spinner: Spinner, list: Array<String>) {
    spinner.adapter = ArrayAdapter<Any?>(spinner.context, android.R.layout.simple_spinner_dropdown_item, list)
}

@BindingAdapter(value = ["selectedValue", "selectedValueAttrChanged"], requireAll = false)
fun bindSpinnerData(
    pAppCompatSpinner: Spinner,
    newSelectedValue: String,
    newTextAttrChanged: InverseBindingListener
) {
    pAppCompatSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            newTextAttrChanged.onChange()
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
    val pos = (pAppCompatSpinner.adapter as ArrayAdapter<String>).getPosition(newSelectedValue)
    pAppCompatSpinner.setSelection(pos, true)
}

@InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
fun captureSelectedValue(pAppCompatSpinner: AppCompatSpinner): String {
    return pAppCompatSpinner.selectedItem as String
}