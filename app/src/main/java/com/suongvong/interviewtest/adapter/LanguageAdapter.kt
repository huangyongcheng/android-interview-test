package com.suongvong.interviewtest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.suongvong.interviewtest.R
import com.suongvong.interviewtest.model.LanguageItem

class LanguageAdapter(
    private val languages: List<LanguageItem>,
    private val onClick: (LanguageItem) -> Unit
) : RecyclerView.Adapter<LanguageAdapter.LangViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LangViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_language, parent, false)
        return LangViewHolder(view)
    }

    override fun onBindViewHolder(holder: LangViewHolder, position: Int) {
        val language = languages[position]
        holder.bind(language)
    }

    override fun getItemCount(): Int = languages.size

    inner class LangViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivFlag: ImageView = itemView.findViewById(R.id.ivFlag)
        private val tvName: TextView = itemView.findViewById(R.id.tvLanguageName)

        fun bind(languageItem: LanguageItem) {
            ivFlag.setImageResource(languageItem.flagResId)
            tvName.text = languageItem.name
            itemView.setOnClickListener { onClick(languageItem) }
        }
    }
}
