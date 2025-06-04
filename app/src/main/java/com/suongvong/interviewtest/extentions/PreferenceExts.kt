package com.suongvong.interviewtest.extentions

import android.content.Context

import com.suongvong.interviewtest.constants.DEFAULT_LANGUAGE
import com.suongvong.interviewtest.constants.PREF_APP_NAME
import com.suongvong.interviewtest.constants.PREF_KEY_LANGUAGE


fun Context.saveLanguage(value:String) {
    val pref = getSharedPreferences(PREF_APP_NAME, Context.MODE_PRIVATE)
    val editor = pref.edit()
    editor.putString(PREF_KEY_LANGUAGE, value)
    editor.apply()
}

fun Context.getLanguage(): String {
    val pref = getSharedPreferences(PREF_APP_NAME, Context.MODE_PRIVATE)
    return pref.getString(PREF_KEY_LANGUAGE, DEFAULT_LANGUAGE) ?: DEFAULT_LANGUAGE
}



