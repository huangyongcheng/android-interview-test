package com.suongvong.interviewtest.respository.news

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.suongvong.interviewtest.network.NewsApiService
import com.suongvong.interviewtest.network.response.Article
import com.suongvong.interviewtest.ui.base.NetworkState
import com.suongvong.interviewtest.ui.base.OnDataSourceError
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.Executor


open class ItemKeyedNewsCollectionDataSource(val context: Context, private val serverAPI: NewsApiService, private val retryExecutor: Executor) : PageKeyedDataSource<Int, Article>() {
    lateinit var onDataSourceError: OnDataSourceError
    private var compositeDisposable = CompositeDisposable()
    private var retry: (() -> Any)? = null
    private val networkState = MutableLiveData<NetworkState>()
    private val initialLoad = MutableLiveData<NetworkState>()
    private var totalRecordsLoadInitial = 0

    @SuppressLint("CheckResult")
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Article>) {
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)
        serverAPI.getEverything2().subscribe({ newsResponse ->
            totalRecordsLoadInitial = newsResponse?.totalResults ?: 0
            newsResponse?.articles.let {
                it?.let {
                    networkState.postValue(NetworkState.LOADED)
                    initialLoad.postValue(NetworkState.LOADED)
                    callback.onResult(it, null, 2)
                }
            }
        }, {})
    }

    @SuppressLint("CheckResult")
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        if (totalRecordsLoadInitial == 50) {
            networkState.postValue(NetworkState.LOADING)
            serverAPI.getEverything2().subscribe({ newsResponse ->
                totalRecordsLoadInitial = newsResponse?.totalResults ?: 0
                newsResponse?.articles.let {
                    it?.let {
                        networkState.postValue(NetworkState.LOADED)
                        callback.onResult(it, params.key + 1)
                    }
                }
            }, {
                afterError(it)
            })
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
    }


    private fun afterError(error: Throwable) {
        onDataSourceError.onAfterError()
        error.printStackTrace()
    }

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }

    fun onClear() {
        if (compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

}