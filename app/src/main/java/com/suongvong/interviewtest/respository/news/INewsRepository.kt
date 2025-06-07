package com.suongvong.interviewtest.respository.news

import com.suongvong.interviewtest.network.response.Article
import com.suongvong.interviewtest.ui.base.Listing
import com.suongvong.interviewtest.ui.base.OnDataSourceError
import io.reactivex.Observable


interface INewsRepository {
    fun getNews(error: OnDataSourceError): Observable<Listing<Article>>
}