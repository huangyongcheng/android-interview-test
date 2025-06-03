package com.suongvong.interviewtest.ui.base

import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference


abstract class BaseViewModel<N> : ViewModel() {
    private var navigator: WeakReference<N>? = null


    fun getNavigator(): N? {
        return navigator?.get()
    }

    fun setNavigator(navigator: N) {
        this.navigator = WeakReference(navigator)
    }


}