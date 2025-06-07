package com.suongvong.interviewtest.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import androidx.paging.DataSource
import com.suongvong.interviewtest.network.response.Article

object ArticleDataSource {

    fun createDataSource(articles: List<Article>): LiveData<PagedList<Article>> {
        val dataSourceFactory = ArticleDataSourceFactory(articles)
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(50)
            .setPageSize(50)
            .build()
        return LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()
    }

    class ArticleDataSourceFactory(private val articles: List<Article>) : DataSource.Factory<String, Article>() {
        private val sourceLiveData = MutableLiveData<ArticlePageKeyedDataSource>()

        override fun create(): DataSource<String, Article> {
            val source = ArticlePageKeyedDataSource(articles)
            sourceLiveData.postValue(source)
            return source
        }
    }

    class ArticlePageKeyedDataSource(private val articles: List<Article>) : PageKeyedDataSource<String, Article>() {

        override fun loadInitial(
            params: LoadInitialParams<String>,
            callback: LoadInitialCallback<String, Article>
        ) {
            callback.onResult(articles, null, null)
        }

        override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Article>) {
        }

        override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Article>) {
        }
    }
}
