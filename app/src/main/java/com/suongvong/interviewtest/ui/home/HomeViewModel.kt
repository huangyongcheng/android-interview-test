package com.suongvong.interviewtest.ui.home

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

class HomeViewModel : BaseViewModel<HomeNavigator>() {

    private fun <T> handleResponse(
        response: Response<T>,
        onSuccess: (T) -> Unit,
        onFail: (ApiErrorResponse) -> Unit
    ) {
        if (response.isSuccessful) {
            response.body()?.let {
                onSuccess(it)
            } ?: onFail(ApiErrorResponse("Empty response body"))
        } else {
            val errorJson = response.errorBody()?.string()
            val errorResponse = try {
                Gson().fromJson(errorJson, ApiErrorResponse::class.java)
            } catch (e: Exception) {
                ApiErrorResponse("Unknown error")
            }
            onFail(errorResponse)
        }
    }

    fun getNewsEverything(context: Context?) {
        val view = getNavigator() ?: return
        val call = RetrofitClient.instance.getEverything(language = context?.getLanguage())

        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                handleResponse(response,
                    onSuccess = { newsResponse ->
                        view.onGetNewsEverythingSuccessful(newsResponse.articles)
                    },
                    onFail = { errorResponse ->
                        view.onGetNewsEverythingFail(errorResponse)
                    }
                )
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                getNavigator()?.onApiFailure()
            }
        })
    }

    fun getTopHeadlines() {
        val view = getNavigator() ?: return
        val call = RetrofitClient.instance.getTopHeadlines()

        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                handleResponse(response,
                    onSuccess = { newsResponse ->
                        view.onGetTopHeadlinesSuccessful(newsResponse.articles)
                    },
                    onFail = { errorResponse ->
                        view.onGetTopHeadlinesFail(errorResponse)
                    }
                )
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                getNavigator()?.onApiFailure()
            }
        })
    }
}
