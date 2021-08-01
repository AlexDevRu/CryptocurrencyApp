package com.example.kulakov_p4_cryptocurrency_app.views.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.kulakov_p4_cryptocurrency_app.R
import com.example.kulakov_p4_cryptocurrency_app.databinding.FragmentWebviewBinding


class WebViewFragment: BaseFragment<FragmentWebviewBinding>
    (R.layout.fragment_webview) {

    private val args: WebViewFragmentArgs by navArgs()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.webView.webViewClient = WebViewClient()
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                binding.loadProgress = progress
            }
        }
        binding.webView.settings.javaScriptEnabled =  true
        binding.url = args.url
    }
}
