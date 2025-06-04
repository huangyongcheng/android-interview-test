package com.suongvong.interviewtest.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import androidx.paging.DataSource
import com.suongvong.interviewtest.network.response.Article


object ArticleDataSource {


    fun createDataSource(settingCustomization: List<Article>): LiveData<PagedList<Article>> {

        var dataSourceFactory = ContactInfoSourceFactory(settingCustomization)
        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(50)
                .setPageSize(50)
                .build()
        val pagedList = LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()
        return pagedList
    }



    class ContactInfoSourceFactory(var settingHomes: List<Article>?) : DataSource.Factory<String, Article>() {
        val sourceLiveData = MutableLiveData<ItemKeyedContactDataSource>()
        override fun create(): DataSource<String, Article> {
            val source = ItemKeyedContactDataSource(settingHomes)
            sourceLiveData.postValue(source)
            return source
        }
    }

    class ItemKeyedContactDataSource(var settingHomes: List<Article>?) : PageKeyedDataSource<String, Article>() {
        override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, Article>) {
            settingHomes?.let {
                callback.onResult(it, "", "")
            }
        }

        override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Article>) {
        }

        override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Article>) {
        }
    }
}