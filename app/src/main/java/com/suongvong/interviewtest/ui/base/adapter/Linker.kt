package com.suongvong.interviewtest.ui.base.adapter

import androidx.annotation.IntRange
import androidx.annotation.NonNull


interface Linker<T> {
    @IntRange(from = 0)
    fun index(position: Int, @NonNull t: T): Int?
}