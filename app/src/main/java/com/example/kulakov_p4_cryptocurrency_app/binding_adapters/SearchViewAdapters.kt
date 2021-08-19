package com.example.kulakov_p4_cryptocurrency_app.binding_adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.example.kulakov_p4_cryptocurrency_app.utils.SearchDestination
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

const val SEARCHABLE_DESTINATION = "AppSearchData"
@SuppressLint("RestrictedApi")
@BindingAdapter("searchableInfo")
fun SearchView.configureSearchableInfo(destination: SearchDestination) {
    val activity = context.getActivity()
    val appContext = activity?.applicationContext
    val searchManager = appContext?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
    this.setSearchableInfo(searchManager.getSearchableInfo(activity.componentName))
    this.setAppSearchData(bundleOf(SEARCHABLE_DESTINATION to destination))
}

fun Context.getActivity(): Activity? {
    return when (this) {
        is Activity -> this
        is ContextWrapper -> this.baseContext.getActivity()
        else -> null
    }
}