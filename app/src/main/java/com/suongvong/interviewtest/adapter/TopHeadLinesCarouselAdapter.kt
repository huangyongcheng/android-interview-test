package com.suongvong.interviewtest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.suongvong.interviewtest.R
import com.suongvong.interviewtest.network.response.Article

class TopHeadLinesCarouselAdapter(val context: Context?, private val items: List<Article>, val listener:TopHeadLinesCarouselLister) :
    RecyclerView.Adapter<TopHeadLinesCarouselAdapter.CarouselViewHolder>() {

    interface TopHeadLinesCarouselLister{
        fun onItemTopHeadlineClick(article: Article)
    }
    inner class CarouselViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val imgThumbnail: ImageView = itemView.findViewById(R.id.imgThumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.top_head_line_item_layout, parent, false)
        return CarouselViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val article = items[position]
        holder.tvTitle.text = article.title
        if (context != null) {
            Glide.with(context).load(article.urlToImage).into(holder.imgThumbnail)
        }

        holder.itemView.setOnClickListener {
            listener.onItemTopHeadlineClick(article)
        }




    }

    override fun getItemCount(): Int = items.size
}
