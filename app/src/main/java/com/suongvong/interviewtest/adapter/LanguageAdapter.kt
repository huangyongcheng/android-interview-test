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

    inner class LangViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivFlag: ImageView = itemView.findViewById(R.id.ivFlag)
        val tvName: TextView = itemView.findViewById(R.id.tvLanguageName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LangViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_language, parent, false)
        return LangViewHolder(view)
    }

    override fun getItemCount() = languages.size

    override fun onBindViewHolder(holder: LangViewHolder, position: Int) {
        val lang = languages[position]
        holder.ivFlag.setImageResource(lang.flagResId)
        holder.tvName.text = lang.name
        holder.itemView.setOnClickListener { onClick(lang) }
    }
}