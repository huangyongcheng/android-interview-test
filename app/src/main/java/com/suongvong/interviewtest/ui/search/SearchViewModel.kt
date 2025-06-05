package com.suongvong.interviewtest.ui.search

import android.content.Context
import com.google.gson.Gson
import com.suongvong.interviewtest.enums.SearchType
import com.suongvong.interviewtest.extentions.getLanguage
import com.suongvong.interviewtest.model.SearchParams
import com.suongvong.interviewtest.network.RetrofitClient
import com.suongvong.interviewtest.network.response.ApiErrorResponse
import com.suongvong.interviewtest.network.response.NewsResponse
import com.suongvong.interviewtest.ui.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : BaseViewModel<SearchNavigator>() {

    fun searchNews(context: Context?, searchParams: SearchParams?) {
        val view = getNavigator() ?: return
        val call: Call<NewsResponse> = if(searchParams?.searchType == SearchType.EVERYTHING) {
            RetrofitClient.instance.getEverything(
                searchKey = searchParams.keyword,
                language = context?.getLanguage(),
                fromDate = searchParams.fromDate,
                toDate = searchParams.toDate,
                sortBy = searchParams.sortBy,
                searchIn = searchParams.searchIns?.joinToString(",") ?: ""
            )
        } else {
            RetrofitClient.instance.getTopHeadlines(
                searchKey = searchParams?.keyword,
                language = context?.getLanguage(),
                category = searchParams?.category
            )
        }

        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {
                if (response.isSuccessful) {
                    val articles = response.body()?.articles ?: emptyList()
                    view.onGetNewsEverythingSuccessful(articles)
                } else {
                    val errorResponse = Gson().fromJson(response.errorBody()?.string(), ApiErrorResponse::class.java)
                    view.onGetNewsEverythingFail(errorResponse)
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                view.onApiFailure()
            }
        })
    }

}