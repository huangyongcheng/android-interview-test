package com.suongvong.interviewtest.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.suongvong.interviewtest.R

class LoadingDialog(private val context: Context?) {
    private var dialog: Dialog? = null

    fun show() {
        context?.let {
            val view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null)
            dialog = Dialog(context)
            dialog?.apply {
                setContentView(view)
                setCancelable(false)
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                show()
            }
        }

    }

    fun dismiss() {
        dialog?.dismiss()
    }
}
