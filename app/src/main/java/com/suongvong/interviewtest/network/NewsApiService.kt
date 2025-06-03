package com.suongvong.interviewtest.network

import com.suongvong.interviewtest.network.response.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    companion object {
        const val TOP_HEADLINES = "v2/top-headlines"

    }

    @GET(TOP_HEADLINES)
    fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("category") category: String = "business",
        @Query("apiKey") apiKey: String = "bd462b505fab472cbe424397dbaa97a9"
    ): Call<NewsResponse>
}