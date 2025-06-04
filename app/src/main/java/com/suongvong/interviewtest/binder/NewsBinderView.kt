package com.suongvong.interviewtest.binder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.suongvong.interviewtest.R
import com.suongvong.interviewtest.network.response.Article
import com.suongvong.interviewtest.ui.base.adapter.ItemViewBinder

class NewsBinderView(var listener: OnItemClickListener) : ItemViewBinder<Article, NewsBinderView.ViewHolder>() {
    companion object {
        private const val MIN_CLICK_INTERVAL: Long = 1000
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.layout_news_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: Article?) {

        item?.let { holder.setData(it) }
    }

    override fun onBindViewHolder(holder: ViewHolder, item: Article?, payloads: MutableList<Any>) {
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imageThumbnail: ImageView = itemView.findViewById(R.id.imageThumbnail)
        private var imageArrow: ImageView = itemView.findViewById(R.id.imageArrow)

        private var textTitle: TextView = itemView.findViewById(R.id.textTitle)
        private var textDescription: TextView = itemView.findViewById(R.id.textDescription)





        fun setData(article: Article?) {



            textTitle.text = article?.title
            textDescription.text = article?.description
            Glide.with(itemView.context).load(article?.urlToImage).into(imageThumbnail)

            itemView.setOnClickListener {
                listener.onItemClick(article)
            }

        }

        private fun getContext(): Context {
            return itemView.context
        }
    }

    interface OnItemClickListener {
        fun onItemClick(contact: Article?)
    }
}