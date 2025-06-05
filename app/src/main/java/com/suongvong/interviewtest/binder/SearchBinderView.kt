package com.suongvong.interviewtest.binder

import android.content.Context
import android.graphics.Bitmap
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

class SearchBinderView(var listener: OnItemClickListener) : ItemViewBinder<Article, SearchBinderView.ViewHolder>() {
    companion object {
        private const val MIN_CLICK_INTERVAL: Long = 1000
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.layout_search_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, item: Article?) {

        item?.let { holder.setData(it) }
    }

    override fun onBindViewHolder(holder: ViewHolder, item: Article?, payloads: MutableList<Any>) {
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imageThumbnail: ImageView = itemView.findViewById(R.id.imageThumbnail)

        private var textTitle: TextView = itemView.findViewById(R.id.textTitle)
        private var textDescription: TextView = itemView.findViewById(R.id.textDescription)





        fun setData(article: Article?) {



            textTitle.text = article?.title
            textDescription.text = article?.description
            Glide.with(itemView.context).load(article?.urlToImage).into(imageThumbnail)

            val heightsDp = listOf(150, 200, 300)
            val randomHeightDp = heightsDp.random()

            // Convert dp to pixel
            val context = itemView.context
            val scale = context.resources.displayMetrics.density
            val heightPx = (randomHeightDp * scale).toInt()

            val params = imageThumbnail.layoutParams
            params.height = heightPx
            imageThumbnail.layoutParams = params

//            val heightsDp = listOf(150, 200, 300)
//            val randomHeightDp = heightsDp.random()
//            val context = holder.itemView.context
//            val scale = context.resources.displayMetrics.density
//            val heightPx = (randomHeightDp * scale).toInt()
//
//            Glide.with(context)
//                .asBitmap()
//                .load(article.urlToImage)
//                .into(object : CustomTarget<Bitmap>() {
//                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                        // Set ảnh cho imageView
//                        holder.imageThumbnail.setImageBitmap(resource)
//
//                        // Set chiều cao ngẫu nhiên
//                        val params = holder.imageThumbnail.layoutParams
//                        params.height = heightPx
//                        holder.imageThumbnail.layoutParams = params
//                    }
//
//                    override fun onLoadCleared(placeholder: Drawable?) {
//                        // Optional: clear ảnh
//                    }
//                })

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