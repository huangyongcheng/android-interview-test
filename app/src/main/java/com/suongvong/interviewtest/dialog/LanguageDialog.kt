package com.suongvong.interviewtest.dialog

import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.suongvong.interviewtest.R
import com.suongvong.interviewtest.adapter.LanguageAdapter
import com.suongvong.interviewtest.extentions.saveLanguage
import com.suongvong.interviewtest.model.LanguageItem

class LanguageDialog(
    private val context: Context,
    private val languages: List<LanguageItem>,
    private val onLanguageSelected: (LanguageItem) -> Unit
) {

    private var alertDialog: AlertDialog? = null

    fun show() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_language_list, null)
        val recyclerView = dialogView.findViewById<RecyclerView>(R.id.rvLanguages)
        val btnClose= dialogView.findViewById<Button>(R.id.btnClose)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = LanguageAdapter(languages) { selected ->
            context.saveLanguage(selected.code)
            onLanguageSelected(selected)
            alertDialog?.dismiss()
        }

        alertDialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .create()

        btnClose.setOnClickListener {
            alertDialog?.dismiss()
        }

        alertDialog?.show()
    }
}