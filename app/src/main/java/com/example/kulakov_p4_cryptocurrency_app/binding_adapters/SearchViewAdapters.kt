package com.example.kulakov_p4_cryptocurrency_app.binding_adapters

import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import java.util.*

@BindingAdapter("query")
fun setQuery(searchView: SearchView, query: String?) {
    if (Objects.equals(searchView.query.toString(), query)) {
        return
    }
    searchView.setQuery(query, false)
}

@InverseBindingAdapter(attribute = "query", event = "queryAttrChanged")
fun getQuery(searchView: SearchView): String {
    return searchView.query.toString()
}

@BindingAdapter("queryAttrChanged")
fun setQueryAttrChanged(searchView: SearchView, queryAttrChanged: InverseBindingListener) {
    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean = false

        override fun onQueryTextChange(newText: String): Boolean {
            queryAttrChanged.onChange()
            return false
        }
    })
}
