package com.suongvong.interviewtest.ui.base.adapter

import androidx.recyclerview.widget.RecyclerView


interface Adapter {
    fun <T> register(clazz: Class<out T>, binder: ItemViewBinder<T, RecyclerView.ViewHolder>)

    fun <T> register(clazz: Class<out T>, binder: ItemViewBinder<T, RecyclerView.ViewHolder>, linker: Linker<T>)


    fun getRawBinderByViewHolder(holder: RecyclerView.ViewHolder): ItemViewBinder<*, RecyclerView.ViewHolder>?

    fun checkAndRemoveAllTypesIfNeeded(clazz: Class<*>)

    fun <T> registerWithoutChecking(clazz: Class<out T>, binder: ItemViewBinder<T, RecyclerView.ViewHolder>, linker: Linker<T>)

    @Throws(BinderNotFoundException::class)

    fun indexInTypesOf(position: Int, item: Any): Int
}