package com.suongvong.interviewtest.ui.webview

import com.suongvong.interviewtest.network.response.Article
import com.suongvong.interviewtest.ui.base.BaseView


interface WebViewNavigator : BaseView {
    fun onGetTopHeadlinesSuccessful(articles: List<Article>)
    fun onGetTopHeadlinesFail()
}