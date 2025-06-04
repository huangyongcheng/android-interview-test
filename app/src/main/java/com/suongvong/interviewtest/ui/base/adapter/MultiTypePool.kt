package com.suongvong.interviewtest.ui.base.adapter

import androidx.recyclerview.widget.RecyclerView


class MultiTypePool : TypePool {
    private var classes: MutableList<Class<*>>
    private var binders: MutableList<ItemViewBinder<*, RecyclerView.ViewHolder>>
    private var linkers: MutableList<Linker<*>>

    constructor() {
        this.classes = mutableListOf()
        this.binders = mutableListOf()
        this.linkers = mutableListOf()
    }

    constructor(initialCapacity: Int) {
        this.classes = ArrayList(initialCapacity)
        this.binders = ArrayList(initialCapacity)
        this.linkers = ArrayList(initialCapacity)
    }

    constructor(
        classes: MutableList<Class<*>>,
        binders: MutableList<ItemViewBinder<*, RecyclerView.ViewHolder>>,
        linkers: MutableList<Linker<*>>
    ) {
        checkNotNull(classes)
        checkNotNull(binders)
        checkNotNull(linkers)
        this.classes = classes
        this.binders = binders
        this.linkers = linkers
    }

    override fun <T> register(
        clazz: Class<out T>,
        binder: ItemViewBinder<T, RecyclerView.ViewHolder>,
        linker: Linker<T>
    ) {
        checkNotNull(classes)
        checkNotNull(binders)
        checkNotNull(linkers)
        classes.add(clazz)
        binders.add(binder)
        linkers.add(linker)
    }

    override fun unregister(clazz: Class<*>): Boolean {
        checkNotNull(clazz)
        var removed = false
        while (true) {
            val index = classes.indexOf(clazz)
            if (index != -1) {
                index.let { classes.removeAt(it) }
                index.let { binders.removeAt(it) }
                index.let { linkers.removeAt(it) }
                removed = true
            } else {
                break
            }
        }
        return removed
    }

    override fun size(): Int {
        return classes.size
    }

    override fun firstIndexOf(clazz: Class<*>): Int {
        checkNotNull(clazz)
        val index = classes.indexOf(clazz)
        if (index != -1) {
            return index
        }
        for (i in classes.indices) {
            if (classes[i].isAssignableFrom(clazz)) {
                return i
            }
        }
        return -1
    }

    override fun getClass(index: Int): Class<*> {
        return classes[index]
    }

    override fun getItemViewBinder(index: Int): ItemViewBinder<*, RecyclerView.ViewHolder> {
        return binders[index]
    }

    override fun getLinker(index: Int): Linker<*> {
        return linkers[index]
    }
}