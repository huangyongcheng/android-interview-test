package com.suongvong.interviewtest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.suongvong.interviewtest.R
import com.suongvong.interviewtest.network.response.Article

class TopHeadLinesCarouselAdapter(
    private val items: List<Article>,
    private val listener: TopHeadLinesCarouselListener
) : RecyclerView.Adapter<TopHeadLinesCarouselAdapter.CarouselViewHolder>() {

    interface TopHeadLinesCarouselListener {
        fun onItemTopHeadlineClick(article: Article)
    }

    inner class CarouselViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val imgThumbnail: ImageView = itemView.findViewById(R.id.imgThumbnail)

        fun bind(article: Article) {
            tvTitle.text = article.title
            Glide.with(itemView.context)
                .load(article.urlToImage)
                .into(imgThumbnail)

            itemView.setOnClickListener {
                listener.onItemTopHeadlineClick(article)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.top_head_line_item_layout, parent, false)
        return CarouselViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
