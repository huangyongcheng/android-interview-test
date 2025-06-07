package com.suongvong.interviewtest.ui.category

import android.content.Context
import com.google.gson.Gson
import com.suongvong.interviewtest.extentions.getLanguage
import com.suongvong.interviewtest.network.RetrofitClient
import com.suongvong.interviewtest.network.response.ApiErrorResponse
import com.suongvong.interviewtest.network.response.NewsResponse
import com.suongvong.interviewtest.ui.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel : BaseViewModel<CategoryNavigator>() {

    fun getTopHeadlines(category:String) {
        val view = getNavigator() ?: return
        val call = RetrofitClient.instance.getTopHeadlines(category =  category)

        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {
                if (response.isSuccessful) {
                    val articles = response.body()?.articles ?: emptyList()
                    view.onGetTopHeadlinesSuccessful(articles)
                } else {
                    val errorResponse = Gson().fromJson(response.errorBody()?.string(), ApiErrorResponse::class.java)
                    view.onGetTopHeadlinesFail(errorResponse)
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                view.onApiFailure()
            }
        })
    }

}