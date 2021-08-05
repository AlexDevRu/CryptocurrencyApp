package com.example.kulakov_p4_cryptocurrency_app.binding_adapters

/*import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import io.reactivex.subjects.BehaviorSubject

@BindingAdapter("doubleNumber")
fun rxText(editText: EditText, subject: BehaviorSubject<Double>) {

    editText.text = Editable.Factory.getInstance().newEditable(subject.value.toString())

    editText.addTextChangedListener {
        Log.w("asd", "edit $it")
        subject.onNext(if(it.isNullOrEmpty()) 0.0 else it.toString().toDouble())
    }
}*/

import android.text.Editable
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

@BindingAdapter("doubleNumber")
fun setDoubleNumber(editText: EditText, number: Double) {

    if(editText.text.isNullOrEmpty() || editText.text.toString() == ".") {
        editText.text = Editable.Factory.getInstance().newEditable("0.0")
        return
    }

    val oldValue = editText.text.toString().toDouble()

    if (oldValue.compareTo(number) == 0) {
        return
    }

    editText.text = Editable.Factory.getInstance().newEditable(number.toString())
}

@InverseBindingAdapter(attribute = "doubleNumber", event = "doubleNumberAttrChanged")
fun getDoubleNumber(editText: EditText): Double {
    if(editText.text.isNullOrEmpty())
        return 0.0
    return try {
        editText.text.toString().toDouble()
    } catch(e: NumberFormatException) {
        0.0
    }
}

@BindingAdapter("doubleNumberAttrChanged")
fun setDoubleNumberAttrChanged(editText: EditText, doubleNumberAttrChanged: InverseBindingListener) {
    editText.doAfterTextChanged {
        doubleNumberAttrChanged.onChange()
    }
}