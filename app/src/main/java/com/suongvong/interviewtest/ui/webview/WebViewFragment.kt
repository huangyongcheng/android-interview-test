package com.suongvong.interviewtest.ui.webview

import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.suongvong.interviewtest.R
import com.suongvong.interviewtest.dialog.LoadingDialog
import com.suongvong.interviewtest.ui.base.BaseFragment

class WebViewFragment : BaseFragment<WebViewViewModel>() {

    private var webView: WebView? = null
    private var url: String? = null
    private var loadingDialog: LoadingDialog? = null

    override fun getLayoutId(): Int = R.layout.fragment_webview

    override fun setupViewModel(): WebViewViewModel {
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return WebViewViewModel() as T
            }
        })[WebViewViewModel::class.java]
        return viewModel
    }

    override fun setupView() {
        super.setupView()
        webView = findViewById(R.id.webView) as WebView
        loadingDialog = LoadingDialog(context)
        setupWebView()
    }

    private fun setupWebView() {
        webView?.apply {
            settings.javaScriptEnabled = true

            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    loadingDialog?.dismiss()
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    loadingDialog?.dismiss()
                    // Optional: Show error message here if needed
                }
            }
        }
    }

    override fun getArgumentIntent() {
        super.getArgumentIntent()
        url = arguments?.let {
            WebViewFragmentArgs.fromBundle(it).url
        }
    }

    override fun setupData(savedInstanceState: Bundle?) {
        loadingDialog?.show()
        loadWebView()
    }

    private fun loadWebView() {
        url?.let { webView?.loadUrl(it) }
    }

    override fun onDestroyView() {
        webView?.apply {
            stopLoading()
            destroy()
        }
        webView = null
        super.onDestroyView()
    }

}
