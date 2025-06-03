package com.suongvong.interviewtest.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.suongvong.interviewtest.R
import com.suongvong.interviewtest.network.RetrofitClient
import com.suongvong.interviewtest.network.response.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment: Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getNews()
    }

    private fun getNews() {
        val call = RetrofitClient.instance.getTopHeadlines()

        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {
                if (response.isSuccessful) {
                    val articles = response.body()?.articles ?: emptyList()
                    for (article in articles) {
                        Log.d("News", article.title ?: "No title")
                    }
                } else {
                    Log.e("NewsError", "Response not successful: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                Log.e("NewsError", "API call failed: ${t.message}")
            }
        })
    }
}