package com.example.kulakov_p4_cryptocurrency_app.utils

import androidx.databinding.Observable

class PropertyChangedCallback(private val callback: (propertyId: Int) -> Unit)
    : Observable.OnPropertyChangedCallback() {

    override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
        callback(propertyId)
    }
}