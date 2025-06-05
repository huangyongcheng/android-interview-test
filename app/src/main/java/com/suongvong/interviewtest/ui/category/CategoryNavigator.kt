package com.suongvong.interviewtest.ui.category

import com.suongvong.interviewtest.network.response.Article
import com.suongvong.interviewtest.ui.base.BaseView


interface CategoryNavigator : BaseView {

    fun onGetNewsEverythingSuccessful(articles: List<Article>)
    fun onGetNewsEverythingFail()
}