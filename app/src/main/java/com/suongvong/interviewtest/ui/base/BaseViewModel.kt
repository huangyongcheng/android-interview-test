package com.suongvong.interviewtest.ui.base

import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

abstract class BaseViewModel<N> : ViewModel() {

    private var navigatorRef: WeakReference<N>? = null
    fun setNavigator(navigator: N) {
        this.navigatorRef = WeakReference(navigator)
    }

    fun getNavigator(): N? = navigatorRef?.get()


    open fun getListener(): OnDataSourceError = object : OnDataSourceError {
        override fun onInitialError() {}
        override fun onAfterError() {}
    }

    override fun onCleared() {
        super.onCleared()
    }
}
