package com.suongvong.interviewtest.respository.news

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import android.content.Context
import com.suongvong.interviewtest.network.NewsApiService
import com.suongvong.interviewtest.network.response.Article
import com.suongvong.interviewtest.ui.base.OnDataSourceError
import java.util.concurrent.Executor


class NewsDataSourceFactory(val context: Context, val serverAPI: NewsApiService, private val retryExecutor: Executor, private var error: OnDataSourceError) : DataSource.Factory<Int, Article>() {
    val sourceLiveData = MutableLiveData<ItemKeyedNewsCollectionDataSource>()
    override fun create(): DataSource<Int, Article> {
        val source = ItemKeyedNewsCollectionDataSource(context, serverAPI, retryExecutor)
        source.onDataSourceError = error
        sourceLiveData.postValue(source)
        return source
    }
}