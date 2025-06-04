package com.suongvong.interviewtest.ui.base.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup


abstract class ItemViewBinder<T, VH : RecyclerView.ViewHolder> {
    var adapter: Adapter? = null
    abstract fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH

    abstract fun onBindViewHolder(holder: VH, item: T?)
    abstract fun onBindViewHolder(holder: VH, item: T?, payloads: MutableList<Any>)
    protected fun getPosition(holder: RecyclerView.ViewHolder): Int {
        return holder.adapterPosition
    }

    fun getItemId(item: T?): Long {
        return RecyclerView.NO_ID
    }

    fun onViewRecycled(holder: VH) {
    }

    fun onFailedToRecycleView(holder: VH): Boolean {
        return false
    }

    fun onViewAttachedToWindow(holder: VH) {}
    fun onViewDetachedFromWindow(holder: VH) {}
}