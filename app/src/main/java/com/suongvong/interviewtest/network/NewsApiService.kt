package com.suongvong.interviewtest.network

import com.suongvong.interviewtest.constants.API_KEY
import com.suongvong.interviewtest.constants.DEFAULT_COUNTRY
import com.suongvong.interviewtest.constants.DEFAULT_LANGUAGE
import com.suongvong.interviewtest.constants.DEFAULT_PAGE
import com.suongvong.interviewtest.constants.DEFAULT_PAGE_SIZE
import com.suongvong.interviewtest.network.response.NewsResponse
import com.suongvong.interviewtest.network.response.NewsSourceResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    companion object {
        const val TOP_HEADLINES = "v2/top-headlines"
        const val TOP_HEADLINE_SOURCES = "v2/top-headlines/sources"
        const val EVERYTHING = "v2/everything"

    }


    @GET(EVERYTHING)
    fun getEverything(
        @Query("q") searchKey: String? = "today",
        @Query("from") fromDate: String? = null,
        @Query("to") toDate: String? = null,
        @Query("sortBy") sortBy: String? = null,
        @Query("language") language: String? = DEFAULT_LANGUAGE,
        @Query("searchIn") searchIn: String? = null,
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("page") page: Int = DEFAULT_PAGE,
        @Query("pageSize") pageSize: Int = DEFAULT_PAGE_SIZE

    ): Call<NewsResponse>

    @GET(EVERYTHING)
    fun getEverything2(
        @Query("q") searchKey: String? = "today",
        @Query("from") fromDate: String? = null,
        @Query("to") toDate: String? = null,
        @Query("sortBy") sortBy: String? = null,
        @Query("language") language: String? = DEFAULT_LANGUAGE,
        @Query("searchIn") searchIn: String? = null,
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("page") page: Int = DEFAULT_PAGE,
        @Query("pageSize") pageSize: Int = DEFAULT_PAGE_SIZE

    ): Observable<NewsResponse>

    @GET(TOP_HEADLINES)
    fun getTopHeadlines(
        @Query("q") searchKey: String? = null,
        @Query("country") country: String = DEFAULT_COUNTRY,
        @Query("category") category: String? = null,
        @Query("language") language: String? = DEFAULT_LANGUAGE,
        @Query("apiKey") apiKey: String = API_KEY
    ): Call<NewsResponse>

    @GET(TOP_HEADLINE_SOURCES)
    fun getTopHeadlineSources(
        @Query("country") country: String = DEFAULT_COUNTRY,
        @Query("language") language: String? = DEFAULT_LANGUAGE,
        @Query("apiKey") apiKey: String = API_KEY
    ): Call<NewsSourceResponse>

}