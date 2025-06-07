package com.suongvong.interviewtest.extentions

import android.content.Context
import android.content.res.Resources
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.suongvong.interviewtest.interfaces.OnTabSelectedListener
import com.suongvong.interviewtest.interfaces.OnTextChangedListener
import kotlin.math.abs

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

fun ViewPager2.addCarouselEffect(enableZoom: Boolean = true) {
    clipChildren = false
    clipToPadding = false
    offscreenPageLimit = 3
    (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER // Remove the scroll effect

    val compositePageTransformer = CompositePageTransformer()
    compositePageTransformer.addTransformer(MarginPageTransformer((10 * Resources.getSystem().displayMetrics.density).toInt()))
    if (enableZoom) {
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = (0.80f + r * 0.20f)
        }
    }
    setPageTransformer(compositePageTransformer)
}