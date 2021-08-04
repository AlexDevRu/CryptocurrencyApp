package com.example.kulakov_p4_cryptocurrency_app.binding_adapters

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import io.reactivex.subjects.BehaviorSubject

/*@BindingAdapter("doubleNumber")
fun rxText(editText: EditText, subject: BehaviorSubject<Double>) {

    editText.text = Editable.Factory.getInstance().newEditable(subject.value.toString())

    editText.addTextChangedListener {
        Log.w("asd", "edit $it")
        subject.onNext(if(it.isNullOrEmpty()) 0.0 else it.toString().toDouble())
    }
}*/

@BindingAdapter("doubleNumber")
fun setDoubleInTextView(tv: EditText, dbl: Double?) {

    try {
        //This will occur when view is first created or when the leading zero is omitted
        if (dbl == null && (tv.text.toString() == "" || tv.text.toString() == ".")) return

        //Check to see what's already there
        val tvDbl = tv.text.toString().toDouble()
        //If it's the same as what we've just entered then return
        // This is when then the double was typed rather than binded
        if (tvDbl == dbl)
            return

        //If it's a new number then set it in the tv
        tv.text = Editable.Factory.getInstance().newEditable(if(dbl == null) "0.0" else dbl.toString())

    } catch (nfe: java.lang.NumberFormatException) {

        //This is usually caused when tv.text is blank and we've entered the first digit
        tv.text = Editable.Factory.getInstance().newEditable(if(dbl == null) "0.0" else dbl.toString()) ?:
        Editable.Factory.getInstance().newEditable("0.0")

    }//catch

}//setDoubleInTextView

//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -//

@InverseBindingAdapter(attribute = "doubleNumber")
fun getDoubleFromTextView(editText: EditText): Double? {

    return try {
        editText.text.toString().toDouble()
    } catch (e: NumberFormatException) {
        null
    }//catch

}//getDoubleFromTextView

//-------------------------------------------------------------------------------------------------//

@BindingAdapter("doubleNumberAttrChanged")
fun setTextChangeListener(editText: EditText, listener: InverseBindingListener) {
    editText.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) = listener.onChange()

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            Log.d("asd", "beforeTextChanged $p0")
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            Log.d("asd", "onTextChanged $p0")
        }

    })
}