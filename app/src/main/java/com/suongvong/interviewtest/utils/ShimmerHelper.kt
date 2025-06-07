package com.suongvong.interviewtest.utils

import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout

/**
 * This helper leverages Shimmer to animate shimmer effects over views
 *
 *A utility object that provides helper methods to apply shimmer effects to views,
 * typically used to indicate loading placeholders in a UI.
 * .
 */
object ShimmerHelper {

    /**
     * Configures a [ShimmerFrameLayout] with a default shimmer effect using alpha highlight.
     *
     * @param shimmerContainer The [ShimmerFrameLayout] to configure.
     */
    private fun configShimmer(shimmerContainer: ShimmerFrameLayout) {
        val shimmer = Shimmer.AlphaHighlightBuilder()
            .setBaseAlpha(0.7f)
            .build()
        shimmerContainer.setShimmer(shimmer)
    }

    /**
     * Start shimmer by view
     *
     * Recursively finds all [ShimmerFrameLayout] instances in the view hierarchy starting from [view],
     * configures them with shimmer, and starts the shimmer animation.
     *
     * @param view The root view to start searching from.
     */
    fun findAndStartShimmer(view: View?) {
        if (view is ShimmerFrameLayout) {
            configShimmer(view)
            Handler(Looper.getMainLooper()).post {
                view.startShimmer()
            }
            for (i in 0 until view.childCount) {
                view.getChildAt(i).visibility = View.INVISIBLE
            }
        } else if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                findAndStartShimmer(view.getChildAt(i))
            }
        }
    }

    /**
     * Stop shimmer by view
     *
     * Recursively finds all [ShimmerFrameLayout] instances in the view hierarchy starting from [view]
     * and stops the shimmer animation. The child views of each [ShimmerFrameLayout] will remain invisible.
     *
     * @param view The root view to start searching from.
     */
    fun findAndStopShimmer(view: View?) {
        if (view is ShimmerFrameLayout) {
            Handler(Looper.getMainLooper()).post {
                view.stopShimmer()
            }
            for (i in 0 until view.childCount) {
                view.getChildAt(i).visibility = View.INVISIBLE
            }
        } else if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                findAndStartShimmer(view.getChildAt(i))
            }
        }
    }

    /**
     * Generates a mutable list of a given [size] using a generator [creator] function.
     *
     * @param size The size of the list to generate.
     * @param creator A function that returns an element for a given index.
     * @return A [MutableList] of generated elements.
     */
    fun <T> generateList(size: Int, creator: (Int) -> T): MutableList<T> {
        return MutableList(size) { index -> creator(index) }
    }

}