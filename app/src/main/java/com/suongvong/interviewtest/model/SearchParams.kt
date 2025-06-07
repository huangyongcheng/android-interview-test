package com.suongvong.interviewtest.model

import android.os.Parcelable
import com.suongvong.interviewtest.enums.SearchType
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchParams(
    val searchType: SearchType?,
    val keyword: String?,
    val fromDate: String?,
    val toDate: String?,
    val category: String?,
    val searchIns :List<String>?,
    val sortBy: String?
):Parcelable
