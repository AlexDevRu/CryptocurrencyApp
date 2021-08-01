package com.example.kulakov_p4_cryptocurrency_app.binding_adapters

import android.webkit.WebView
import androidx.databinding.BindingAdapter

@BindingAdapter("url")
fun bindUrl(webView: WebView, url: String) {
    webView.loadUrl(url)
}