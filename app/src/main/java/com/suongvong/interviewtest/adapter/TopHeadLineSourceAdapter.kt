package com.suongvong.interviewtest.adapter

import androidx.recyclerview.widget.DiffUtil
import com.suongvong.interviewtest.network.response.NewsSource
import com.suongvong.interviewtest.ui.base.adapter.BaseFlexibleAdapter


class TopHeadLineSourceAdapter : BaseFlexibleAdapter<NewsSource>(SOURCE) {
    companion object {
        private val PAYLOAD_SCORE = Any()
        val SOURCE = object : DiffUtil.ItemCallback<NewsSource>() {
            override fun getChangePayload(oldItem: NewsSource, newItem: NewsSource): Any? {
                return if (sameExceptScore(oldItem, newItem)) {
                    PAYLOAD_SCORE
                } else {
                    null
                }
            }

            private fun sameExceptScore(oldItem: NewsSource, newItem: NewsSource): Boolean {
                return oldItem.copy(url = null) == newItem.copy(url = null)
            }

            override fun areItemsTheSame(oldItem: NewsSource, newItem: NewsSource): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NewsSource, newItem: NewsSource): Boolean {
                return oldItem == newItem
            }


        }
    }
}
