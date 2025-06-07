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
import com.suongvong.interviewtest.network.response.NewsSource
import com.suongvong.interviewtest.ui.base.adapter.ItemViewBinder

class TopHeadLineSourcesBinderView(var listener: OnItemClickListener) : ItemViewBinder<NewsSource, TopHeadLineSourcesBinderView.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.layout_news_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: NewsSource?) {

        item?.let { holder.setData(it) }
    }

    override fun onBindViewHolder(holder: ViewHolder, item: NewsSource?, payloads: MutableList<Any>) {
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imageThumbnail: ImageView = itemView.findViewById(R.id.imageThumbnail)
        private var imageArrow: ImageView = itemView.findViewById(R.id.imageArrow)

        private var textTitle: TextView = itemView.findViewById(R.id.textTitle)
        private var textDescription: TextView = itemView.findViewById(R.id.textDescription)





        fun setData(newsSource: NewsSource?) {


//
//            textTitle.text = newsSource?.
//            textDescription.text = newsSource?.description
//            Glide.with(itemView.context).load(newsSource?.urlToImage).into(imageThumbnail)
//
//            itemView.setOnClickListener {
//                listener.onItemClick(newsSource)
//            }

        }

        private fun getContext(): Context {
            return itemView.context
        }
    }

    interface OnItemClickListener {
        fun onItemClick(contact: NewsSource?)
    }
}