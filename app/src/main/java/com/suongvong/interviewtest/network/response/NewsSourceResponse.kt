package com.suongvong.interviewtest.network.response

data class NewsSourceResponse (
    val status: String,
    val sources: List<NewsSource>
)


data class NewsSource(
    val id: String,
    val name: String?,
    val description: String?,
    val url: String?,
    val category: String?,
    val language: String?,
    val country: String?
)