package com.suongvong.interviewtest.binder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.suongvong.interviewtest.R
import com.suongvong.interviewtest.network.response.Article
import com.suongvong.interviewtest.ui.base.adapter.ItemViewBinder

class SearchBinderView(
    private val listener: OnItemClickListener
) : ItemViewBinder<Article, SearchBinderView.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val view = inflater.inflate(R.layout.layout_search_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: Article?) {
        item?.let { holder.bind(it) }
    }

    override fun onBindViewHolder(holder: ViewHolder, item: Article?, payloads: MutableList<Any>) {
        onBindViewHolder(holder, item)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageThumbnail: ImageView = itemView.findViewById(R.id.imageThumbnail)
        private val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        private val textDescription: TextView = itemView.findViewById(R.id.textDescription)

        fun bind(article: Article) {
            textTitle.text = article.title
            textDescription.text = article.description

            Glide.with(itemView.context)
                .load(article.urlToImage)
                .into(imageThumbnail)

            adjustImageHeightRandomly()

            itemView.setOnClickListener {
                listener.onItemClick(article)
            }
        }

        private fun adjustImageHeightRandomly() {
            val heightsDp = listOf(150, 200, 300)
            val randomHeightDp = heightsDp.random()
            val scale = itemView.context.resources.displayMetrics.density
            val heightPx = (randomHeightDp * scale).toInt()

            val params = imageThumbnail.layoutParams
            params.height = heightPx
            imageThumbnail.layoutParams = params
        }
    }

    interface OnItemClickListener {
        fun onItemClick(article: Article)
    }
}
