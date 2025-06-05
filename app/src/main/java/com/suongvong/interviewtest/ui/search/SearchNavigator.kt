package com.suongvong.interviewtest.ui.search

import com.suongvong.interviewtest.network.response.ApiErrorResponse
import com.suongvong.interviewtest.network.response.Article
import com.suongvong.interviewtest.ui.base.BaseView


interface SearchNavigator : BaseView {


    fun onGetNewsEverythingSuccessful(articles: List<Article>)
    fun onGetNewsEverythingFail(apiErrorResponse: ApiErrorResponse)
}