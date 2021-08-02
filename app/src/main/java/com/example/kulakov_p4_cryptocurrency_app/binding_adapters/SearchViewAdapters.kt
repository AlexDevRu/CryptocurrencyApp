package com.example.kulakov_p4_cryptocurrency_app.binding_adapters

import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter
import io.reactivex.subjects.BehaviorSubject

@BindingAdapter("query")
fun rxText(searchView: SearchView, subject: BehaviorSubject<String>) {

    // Initial value
    searchView.setQuery(subject.value, false)

    // Text changes
    searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String): Boolean {
            subject.onNext(newText)
            return false
        }
    })
}
