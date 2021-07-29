package com.example.kulakov_p4_cryptocurrency_app.binding_adapters

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@BindingAdapter("onRefresh")
fun bindOnSwipeToRefresh(swipeLayout: SwipeRefreshLayout, refreshHandler: () -> Unit) {
    swipeLayout.setOnRefreshListener {
        refreshHandler()
        swipeLayout.isRefreshing = false
    }
}

@BindingAdapter("isRefreshing")
fun bindSwipeToRefresh(swipeLayout: SwipeRefreshLayout, isRefreshing: Boolean) {

}