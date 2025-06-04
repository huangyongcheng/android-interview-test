package com.suongvong.interviewtest.adapter

import androidx.recyclerview.widget.DiffUtil
import com.suongvong.interviewtest.network.response.Article
import com.suongvong.interviewtest.ui.base.adapter.BaseFlexibleAdapter


class NewsAdapter : BaseFlexibleAdapter<Article>(NEWS) {
    companion object {
        private val PAYLOAD_SCORE = Any()
        val NEWS = object : DiffUtil.ItemCallback<Article>() {
            override fun getChangePayload(oldItem: Article, newItem: Article): Any? {
                return if (sameExceptScore(oldItem, newItem)) {
                    PAYLOAD_SCORE
                } else {
                    null
                }
            }

            private fun sameExceptScore(oldItem: Article, newItem: Article): Boolean {
                return oldItem.copy(content = null) == newItem.copy(content = null)
            }

            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.source.id == newItem.source.id
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }


        }
    }
}
