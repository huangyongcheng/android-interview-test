package com.suongvong.interviewtest.ui.webview

import com.suongvong.interviewtest.network.RetrofitClient
import com.suongvong.interviewtest.network.response.NewsResponse
import com.suongvong.interviewtest.ui.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WebViewViewModel : BaseViewModel<WebViewNavigator>() {

     fun getTopHeadlines() {
         val view = getNavigator() ?: return
        val call = RetrofitClient.instance.getTopHeadlines()

        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {
                if (response.isSuccessful) {
                    val articles = response.body()?.articles ?: emptyList()
                    view.onGetTopHeadlinesSuccessful(articles)
                } else {
                    view.onGetTopHeadlinesFail()
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
               view.onGetTopHeadlinesFail()
            }
        })
    }
}