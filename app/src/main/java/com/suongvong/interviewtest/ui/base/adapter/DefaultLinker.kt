package com.suongvong.interviewtest.ui.base.adapter

import androidx.annotation.NonNull

class DefaultLinker<T> : Linker<T> {
    override fun index(position: Int, @NonNull t: T): Int? {
        return 0
    }
}