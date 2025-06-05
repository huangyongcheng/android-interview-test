package com.suongvong.interviewtest.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.suongvong.interviewtest.R
import com.suongvong.interviewtest.adapter.LanguageAdapter
import com.suongvong.interviewtest.extentions.saveLanguage
import com.suongvong.interviewtest.model.LanguageItem

class LanguageDialog(
    context: Context,
    private val languages: List<LanguageItem>,
    private val onLanguageSelected: (LanguageItem) -> Unit
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_language_list)

        val recyclerView = findViewById<RecyclerView>(R.id.rvLanguages)
        val btnClose = findViewById<TextView>(R.id.btnClose)

        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = LanguageAdapter(languages) { selected ->
            context.saveLanguage(selected.code)
            onLanguageSelected(selected)
            dismiss()
        }

        btnClose?.setOnClickListener {
            dismiss()
        }

        setCancelable(true)
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}