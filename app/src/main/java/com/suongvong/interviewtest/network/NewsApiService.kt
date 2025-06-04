package com.suongvong.interviewtest.network

import com.suongvong.interviewtest.constants.API_KEY
import com.suongvong.interviewtest.network.response.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    companion object {
        const val TOP_HEADLINES = "v2/top-headlines"
        const val EVERYTHING = "v2/everything"

    }

    @GET(TOP_HEADLINES)
    fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("category") category: String = "business",
        @Query("apiKey") apiKey: String = API_KEY
    ): Call<NewsResponse>

    @GET(EVERYTHING)
    fun getEverything(
        @Query("q") search: String = "bitcoin",
        @Query("language") language: String? = "zh",
        @Query("apiKey") apiKey: String = API_KEY
    ): Call<NewsResponse>
}