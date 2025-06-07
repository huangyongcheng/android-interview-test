package com.suongvong.interviewtest.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList

object GenericDataSource {

    fun <T> createDataSource(items: List<T>): LiveData<PagedList<T>> {
        val dataSourceFactory = GenericSourceFactory(items)
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(50)
            .setPageSize(50)
            .build()
        return LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()
    }

    class GenericSourceFactory<T>(private val items: List<T>?) : DataSource.Factory<String, T>() {
        val sourceLiveData = MutableLiveData<GenericPageKeyedDataSource<T>>()

        override fun create(): DataSource<String, T> {
            val source = GenericPageKeyedDataSource(items)
            sourceLiveData.postValue(source)
            return source
        }
    }

    class GenericPageKeyedDataSource<T>(private val items: List<T>?) : PageKeyedDataSource<String, T>() {
        override fun loadInitial(
            params: LoadInitialParams<String>,
            callback: LoadInitialCallback<String, T>
        ) {
            items?.let {
                callback.onResult(it, "", "")
            }
        }

        override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, T>) {
            // Implement if needed
        }

        override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, T>) {
            // Implement if needed
        }
    }
}
