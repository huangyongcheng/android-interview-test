package com.suongvong.interviewtest.dialog

import android.content.Context
import android.view.Menu
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import java.util.Locale

object PopupLanguage {
    private val languages = listOf("ar", "de", "en", "es", "fr", "he", "it", "nl", "no", "pt", "ru", "sv", "ud", "zh")

     fun show(context: Context, anchorView: View) {
        val popupMenu = PopupMenu(context, anchorView)

        // Map language codes to display names
        languages.forEachIndexed { index, langCode ->
            val locale = Locale(langCode)
            popupMenu.menu.add(Menu.NONE, index, Menu.NONE, locale.displayLanguage)
        }

        popupMenu.setOnMenuItemClickListener { menuItem ->
            val selectedLangCode = languages[menuItem.itemId]
            Toast.makeText(context, "Selected code: $selectedLangCode", Toast.LENGTH_SHORT).show()
            true
        }

        popupMenu.show()
    }

}