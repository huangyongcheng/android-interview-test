package com.suongvong.interviewtest.ui.home

import com.suongvong.interviewtest.network.response.Article
import com.suongvong.interviewtest.ui.base.BaseView


interface HomeNavigator : BaseView {
    fun onGetTopHeadlinesSuccessful(articles: List<Article>)
    fun onGetTopHeadlinesFail()

    fun onGetNewsEverythingSuccessful(articles: List<Article>)
    fun onGetNewsEverythingFail()
}