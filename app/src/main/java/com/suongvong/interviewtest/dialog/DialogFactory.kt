package com.suongvong.interviewtest.dialog

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.suongvong.interviewtest.R
import com.suongvong.interviewtest.model.LanguageItem
import com.suongvong.interviewtest.model.SearchParams
import java.lang.ref.WeakReference

object DialogFactory {

    fun openLanguageDialog(context: Context?, selectedLanguageItem: (LanguageItem) -> Unit) {
        context?.let {


            val languages = listOf(
                LanguageItem("ar", "Arabic", R.drawable.flag_ar),
                LanguageItem("de", "German", R.drawable.flag_de),
                LanguageItem("en", "English", R.drawable.flag_en),
                LanguageItem("es", "Spanish", R.drawable.flag_es),
                LanguageItem("fr", "French", R.drawable.flag_fr),
                LanguageItem("he", "Hebrew", R.drawable.flag_he),
                LanguageItem("it", "Italian", R.drawable.flag_it),
                LanguageItem("no", "Norwegian", R.drawable.flag_no),
                LanguageItem("pt", "Portuguese", R.drawable.flag_pt),
                LanguageItem("ru", "Russian", R.drawable.flag_ru),
                LanguageItem("sv", "Swedish", R.drawable.flag_sv),
                LanguageItem("zh", "Chinese", R.drawable.flag_zh),

                )

            LanguageDialog(context, languages) { languageItem ->
                selectedLanguageItem.invoke(languageItem)
            }.show()
        }
    }

    fun openSearchNewsDialog(context: Context?, onSearch: (SearchParams) -> Unit) {
        context?.let {

            SearchNewsDialog(context) { result ->
                onSearch.invoke(result)
            }.show()
        }

    }
}