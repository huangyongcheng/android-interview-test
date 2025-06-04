package com.suongvong.interviewtest.utils

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class ZoomOutPageTransformer : ViewPager2.PageTransformer {
    override fun transformPage(view: View, position: Float) {
        val scale = 0.85f.coerceAtLeast(1 - kotlin.math.abs(position))
        view.scaleY = scale
        view.scaleX = scale
        view.alpha = 0.5f + (scale - 0.85f) / (1 - 0.85f) * (1 - 0.5f)
    }
}
