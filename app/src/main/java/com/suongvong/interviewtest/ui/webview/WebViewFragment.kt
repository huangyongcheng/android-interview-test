package com.suongvong.interviewtest.ui.webview

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.suongvong.interviewtest.R
import com.suongvong.interviewtest.binder.NewsBinderView
import com.suongvong.interviewtest.network.response.Article
import com.suongvong.interviewtest.ui.base.BaseFragment


class WebViewFragment : BaseFragment<WebViewViewModel>(), WebViewNavigator, NewsBinderView.OnItemClickListener, View.OnClickListener {


    private var webView: WebView? = null

    private var url: String? = null
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
        webView?.webViewClient = WebViewClient()
        webView?.settings?.javaScriptEnabled = true


    }

    override fun getArgumentIntent() {
        super.getArgumentIntent()
        url = arguments?.let {
            WebViewFragmentArgs.fromBundle(it).url
        }
    }

    override fun setupData(savedInstanceState: Bundle?) {
        viewModel = setupViewModel()
        viewModel.setNavigator(this)
        loadWebView()

    }

    private fun loadWebView() {

        url?.let { webView?.loadUrl(it) }
    }


    override fun onGetTopHeadlinesSuccessful(articles: List<Article>) {


    }

    override fun onGetTopHeadlinesFail() {

    }

    override fun onApiFailure() {
        TODO("Not yet implemented")
    }

    override fun onItemClick(contact: Article?) {
        TODO("Not yet implemented")
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.textUrl -> {

            }
        }
    }


}