package com.suongvong.interviewtest.network.response

data class ApiErrorResponse(
    val status: String? = null,
    val code: Int? = null,
    val message: String? = null
)
