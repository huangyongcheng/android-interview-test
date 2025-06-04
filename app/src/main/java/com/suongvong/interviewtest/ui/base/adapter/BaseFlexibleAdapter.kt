package com.suongvong.interviewtest.ui.base.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter


abstract class BaseFlexibleAdapter<T>(diffUtil: DiffUtil.ItemCallback<T>) : PagedListAdapter<T, RecyclerView.ViewHolder>(diffUtil), Adapter {
    private var typePool: TypePool = MultiTypePool()

    companion object {
        val TAG: String = BaseFlexibleAdapter::class.java.simpleName
    }



    override fun onCreateViewHolder(parent: ViewGroup, indexViewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binder = typePool.getItemViewBinder(indexViewType) as ItemViewBinder<*, *>
        return binder.onCreateViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        val binder = typePool.getItemViewBinder(holder.itemViewType) as ItemViewBinder<Any, RecyclerView.ViewHolder>
        binder.onBindViewHolder(holder, item)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            val item = getItem(position)
            val binder = typePool.getItemViewBinder(holder.itemViewType) as ItemViewBinder<Any, RecyclerView.ViewHolder>
            binder.onBindViewHolder(holder, item, payloads)
        } else {
            onBindViewHolder(holder, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return indexInTypesOf(position, item!!)
    }

    override fun getItemId(position: Int): Long {
        val item = getItem(position)
        val itemViewType = getItemViewType(position)
        val binder = typePool.getItemViewBinder(itemViewType) as ItemViewBinder<Any, RecyclerView.ViewHolder>
        return binder.getItemId(item)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        getRawBinderByViewHolder(holder).onViewRecycled(holder)
    }

    override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean {
        return getRawBinderByViewHolder(holder).onFailedToRecycleView(holder)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        getRawBinderByViewHolder(holder).onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        getRawBinderByViewHolder(holder).onViewDetachedFromWindow(holder)
    }

    override fun <T> register(clazz: Class<out T>, binder: ItemViewBinder<T, RecyclerView.ViewHolder>) {
        checkAndRemoveAllTypesIfNeeded(clazz)
        register(clazz, binder, DefaultLinker())
    }

    override fun <T> register(clazz: Class<out T>, binder: ItemViewBinder<T, RecyclerView.ViewHolder>, linker: Linker<T>) {
        typePool.register(clazz, binder, linker)
        binder.adapter = this
    }

    override fun <T> registerWithoutChecking(clazz: Class<out T>, binder: ItemViewBinder<T, RecyclerView.ViewHolder>, linker: Linker<T>) {
        checkAndRemoveAllTypesIfNeeded(clazz)
        register(clazz, binder, linker)
    }

    override fun getRawBinderByViewHolder(holder: RecyclerView.ViewHolder): ItemViewBinder<*, RecyclerView.ViewHolder> {
        return typePool.getItemViewBinder(holder.itemViewType)
    }

    override fun checkAndRemoveAllTypesIfNeeded(clazz: Class<*>) {
        if (typePool.unregister(clazz)) {
            Log.w(TAG, "You have registered the " + clazz.simpleName + " type. " + "It will override the original binder(s).")
        }
    }

    override fun indexInTypesOf(position: Int, item: Any): Int {
        val index = typePool.firstIndexOf(item.javaClass)
        if (index != -1) {
            @SuppressWarnings("unchecked")
            if (index != null) {
                val linker = typePool.getLinker(index) as Linker<Any>
                return index + linker.index(position, item)!!
            }
        }
        throw BinderNotFoundException(item.javaClass)
    }
}