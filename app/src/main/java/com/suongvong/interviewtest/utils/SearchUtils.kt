package com.suongvong.interviewtest.utils

import android.os.Handler
import android.os.Looper
import androidx.appcompat.widget.SearchView

object SearchUtils {

    private val handler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null

    fun setDebouncedListener(
        searchView: SearchView,
        delayMillis: Long = 3000L,
        onDebouncedInput: (String?) -> Unit
    ) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true // Optional: ignore submit
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Cancel old task
                searchRunnable?.let { handler.removeCallbacks(it) }

                // Post new task with delay
                searchRunnable = Runnable {
                    onDebouncedInput(newText)
                }
                handler.postDelayed(searchRunnable!!, delayMillis)

                return true
            }
        })
    }

    fun cancel() {
        searchRunnable?.let { handler.removeCallbacks(it) }
    }
}
