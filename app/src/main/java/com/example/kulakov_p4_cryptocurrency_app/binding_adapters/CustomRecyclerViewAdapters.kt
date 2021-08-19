package com.example.kulakov_p4_cryptocurrency_app.binding_adapters

import androidx.databinding.BindingAdapter
import com.example.kulakov_p4_cryptocurrency_app.view_models.RecyclerViewVM
import com.example.kulakov_p4_cryptocurrency_app.views.custom.CustomRecyclerView

@BindingAdapter("recyclerViewModel")
fun bindLoading(recyclerView: CustomRecyclerView, viewModel: RecyclerViewVM) {
    recyclerView.viewModel = viewModel
}

@BindingAdapter("onRetry")
fun bindRetryHandler(recyclerView: CustomRecyclerView, retryHandler: () -> Unit) {
    recyclerView.retryHandler = retryHandler
}