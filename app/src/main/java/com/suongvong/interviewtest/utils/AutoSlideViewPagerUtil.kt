package com.suongvong.interviewtest.utils


import android.os.Handler
import android.os.Looper
import androidx.viewpager2.widget.ViewPager2

class AutoSlideViewPagerUtil(
    private val viewPager: ViewPager2?,
    private val intervalMillis: Long = 3000L
) {
    private val handler = Handler(Looper.getMainLooper())
    private var currentPage = 0
    private val runnable = object : Runnable {
        override fun run() {
            val itemCount = viewPager?.adapter?.itemCount ?: 0
            if (itemCount == 0) return

            if (currentPage >= itemCount) {
                currentPage = 0
            }
            viewPager?.setCurrentItem(currentPage++, true)
            handler.postDelayed(this, intervalMillis)
        }
    }

    fun start() {
        stop()
        currentPage = viewPager?.currentItem ?: 0
        handler.postDelayed(runnable, intervalMillis)
    }

    fun stop() {
        handler.removeCallbacks(runnable)
    }
}
