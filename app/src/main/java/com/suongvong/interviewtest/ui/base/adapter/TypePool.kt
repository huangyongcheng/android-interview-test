package com.suongvong.interviewtest.ui.base.adapter

import androidx.recyclerview.widget.RecyclerView

interface TypePool {
    fun <T> register(
        clazz: Class<out T>,
        binder: ItemViewBinder<T, RecyclerView.ViewHolder>,
        linker: Linker<T>
    )

    fun unregister(clazz: Class<*>): Boolean
    fun size(): Int?
    fun firstIndexOf(clazz: Class<*>): Int?
    fun getClass(index: Int): Class<*>

    fun getItemViewBinder(index: Int): ItemViewBinder<*, RecyclerView.ViewHolder>

    fun getLinker(index: Int): Linker<*>
}