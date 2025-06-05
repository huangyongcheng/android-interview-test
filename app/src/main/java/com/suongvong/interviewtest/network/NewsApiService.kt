package com.suongvong.interviewtest.network

import com.suongvong.interviewtest.constants.API_KEY
import com.suongvong.interviewtest.constants.DEFAULT_COUNTRY
import com.suongvong.interviewtest.constants.DEFAULT_LANGUAGE
import com.suongvong.interviewtest.network.response.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    companion object {
        const val TOP_HEADLINES = "v2/top-headlines"
        const val EVERYTHING = "v2/everything"

    }

//    @GET(TOP_HEADLINES)
//    fun getTopHeadlines(
//        @Query("country") country: String = "us",
//        @Query("category") category: String = "business",
//        @Query("apiKey") apiKey: String = API_KEY
//    ): Call<NewsResponse>

//    @GET(EVERYTHING)
//    fun getEverything(
//        @Query("q") searchKey: String = "bitcoin",
//        @Query("language") language: String? = "zh",
//        @Query("apiKey") apiKey: String = API_KEY
//    ): Call<NewsResponse>

    @GET(EVERYTHING)
    fun getEverything(
        @Query("q") searchKey: String? = null,
        @Query("from") fromDate: String? = null,
        @Query("to") toDate: String? = null,
        @Query("sortBy") sortBy: String? = null,
        @Query("language") language: String? = DEFAULT_LANGUAGE,
        @Query("searchIn") searchIn:String?=null,
        @Query("apiKey") apiKey: String = API_KEY
    ): Call<NewsResponse>

    @GET(TOP_HEADLINES)
    fun getTopHeadlines(
        @Query("q") searchKey: String? = null,
        @Query("country") country: String = DEFAULT_COUNTRY,
        @Query("category") category: String? = null,
        @Query("language") language: String? = DEFAULT_LANGUAGE,
        @Query("apiKey") apiKey: String = API_KEY
    ): Call<NewsResponse>

    @GET(EVERYTHING)
    fun searchNews(
        @Query("q") searchKey: String? = "bitcoin",
        @Query("from") fromDate: String?,
        @Query("to") toDate: String?,
        @Query("sortBy") sortBy: String? = "",
        @Query("language") language: String? = DEFAULT_LANGUAGE,
        @Query("apiKey") apiKey: String = API_KEY
    ): Call<NewsResponse>
}