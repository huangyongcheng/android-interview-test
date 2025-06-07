package com.suongvong.interviewtest.ui.base

import androidx.lifecycle.LiveData
import androidx.paging.PagedList


data class Listing<T>(
    val pagedList: LiveData<PagedList<T>>,
    val refresh: () -> Unit,
    val retry: () -> Unit,
    val clear: () -> Unit
)