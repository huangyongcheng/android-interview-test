package com.suongvong.interviewtest.extentions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import com.suongvong.interviewtest.R

fun String.formatTOTPCode(): String {
    return this.replace("(\\d{3})(\\d{3})".toRegex(), "$1 $2")
}

fun String.copyToClipboard(context: Context) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("", this)
    clipboard.setPrimaryClip(clip)
    Toast.makeText(context, context.getString(R.string.app_copied), Toast.LENGTH_SHORT).show()
}