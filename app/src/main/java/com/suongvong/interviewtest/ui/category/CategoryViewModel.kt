package com.suongvong.interviewtest.ui.category

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.suongvong.interviewtest.network.RetrofitClient
import com.suongvong.interviewtest.network.response.ApiErrorResponse
import com.suongvong.interviewtest.network.response.NewsResponse
import com.suongvong.interviewtest.ui.base.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel : BaseViewModel<CategoryNavigator>() {

    private val gson = Gson()

    fun getTopHeadlines(category: String) {
        val navigator = getNavigator() ?: return
        val call = RetrofitClient.instance.getTopHeadlines(category = category)

        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {
                if (response.isSuccessful) {
                    val articles = response.body()?.articles ?: emptyList()
                    navigator.onGetTopHeadlinesSuccessful(articles)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorResponse = parseErrorResponse(errorBody)
                    navigator.onGetTopHeadlinesFail(errorResponse)
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.e("CategoryViewModel", "API call failed: ${t.localizedMessage}", t)
                navigator.onApiFailure()
            }
        })
    }

    private fun parseErrorResponse(errorBody: String?): ApiErrorResponse {
        return try {
            gson.fromJson(errorBody, ApiErrorResponse::class.java)
        } catch (e: JsonSyntaxException) {
            Log.e("CategoryViewModel", "Failed to parse error response", e)
            ApiErrorResponse(message = "Unexpected error occurred")
        } catch (e: Exception) {
            Log.e("CategoryViewModel", "Unknown error", e)
            ApiErrorResponse(message = "Something went wrong")
        }
    }
}
