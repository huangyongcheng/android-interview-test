package com.suongvong.interviewtest.respository.news

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import android.content.Context
import com.suongvong.interviewtest.constants.DEFAULT_PAGE_SIZE
import com.suongvong.interviewtest.network.NewsApiService
import com.suongvong.interviewtest.network.response.Article
import com.suongvong.interviewtest.ui.base.Listing
import com.suongvong.interviewtest.ui.base.OnDataSourceError
import io.reactivex.Observable
import java.util.concurrent.Executor

/**
 * this class I implement for paging, but seem it doesn't work
 */
class NewsRepository(val context: Context, private val serverAPI: NewsApiService, private val retryExecutor: Executor) : INewsRepository {
    override fun getNews(error: OnDataSourceError): Observable<Listing<Article>> {
        val sourceFactory = NewsDataSourceFactory(context, serverAPI, retryExecutor, error)
        val pagedListConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(DEFAULT_PAGE_SIZE)
                .setPageSize(DEFAULT_PAGE_SIZE)
                .build()
        val pagedList = LivePagedListBuilder(sourceFactory, pagedListConfig)
                .setFetchExecutor(retryExecutor)
                .build()
//        val refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {
//            it.initialLoad
//        }
        return Observable.just(Listing(
                pagedList = pagedList,
                retry = {
                    sourceFactory.sourceLiveData.value?.retryAllFailed()
                },
                refresh = {
                    sourceFactory.sourceLiveData.value?.invalidate()
                },
                clear = {
                    sourceFactory.sourceLiveData.value?.onClear()
                }
//            ,
//                networkState = Transformations.switchMap(sourceFactory.sourceLiveData) {
//                    it.networkState
//                },
//                refreshState = refreshState
        ))
    }


}