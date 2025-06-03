package com.suongvong.interviewtest.utils

import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager

object KeyboardUtils {
    fun showKeyboard(view: View?) {
        Handler(Looper.getMainLooper()).postDelayed({
            view?.requestFocus()
            val imm = view?.context?.getSystemService(InputMethodManager::class.java)
            imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }, 200)

    }
}