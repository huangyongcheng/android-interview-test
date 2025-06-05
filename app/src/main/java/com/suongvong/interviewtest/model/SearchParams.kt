package com.suongvong.interviewtest.model

import android.os.Parcelable
import com.suongvong.interviewtest.enums.TypeSearch
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchParams(

    val typeSearch: TypeSearch?,
    val keyword: String?,
    val fromDate: String?,
    val toDate: String?,
    val categories: List<String>?,
    val searchIns :List<String>?,
    val sortBy: String?
):Parcelable
