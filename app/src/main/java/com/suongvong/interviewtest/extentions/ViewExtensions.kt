package com.suongvong.interviewtest.extentions

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.google.android.material.tabs.TabLayout
import com.suongvong.interviewtest.interfaces.OnTabSelectedListener
import com.suongvong.interviewtest.interfaces.OnTextChangedListener

fun EditText.showKeyboard(delayMillis: Long = 100) {
    this.requestFocus()
    this.postDelayed({
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }, delayMillis)
}

fun EditText.setOnTextChangedListener(listener: OnTextChangedListener) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            listener.onTextChanged(s.toString())
        }

        override fun afterTextChanged(s: Editable?) {}
    })
}

fun TabLayout.setOnTabSelected(listener: OnTabSelectedListener) {
    this.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            listener.onTabSelected(tab.position)
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {}
        override fun onTabReselected(tab: TabLayout.Tab) {}
    })
}

fun View.showIf(condition: Boolean) {
    this.post {
        visibility = if (condition) View.VISIBLE else View.GONE
    }

}