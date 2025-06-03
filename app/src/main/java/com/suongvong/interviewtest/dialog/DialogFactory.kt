package com.suongvong.interviewtest.dialog

import android.content.Context
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.suongvong.interviewtest.R
import com.suongvong.interviewtest.constants.TAG_DIALOG_ADD_INFO
import java.lang.ref.WeakReference

object DialogFactory {

    private var currentDialog: WeakReference<AlertDialog>? = null


    fun showInfoDialog(
        context: Context,
        title: String,
        message: String,
        positiveButtonText: String,
        onDismiss: () -> Unit
    ) {
        if (currentDialog?.get()?.isShowing == true) {
            return
        }

        val dialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonText) { dialog, _ ->
                dialog.dismiss()
                onDismiss()
                currentDialog = null
            }
            .create()

        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        currentDialog = WeakReference(dialog)
        dialog.show()

        val positiveButton: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        positiveButton.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
    }

    fun showConfirmationDialog(
        context: Context,
        title: String,
        message: String,
        positiveButtonText: String,
        negativeButtonText: String,
        onConfirmationResult: (Boolean) -> Unit
    ) {
        if (currentDialog?.get()?.isShowing == true) {
            return
        }

        val dialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButtonText) { dialog, _ ->
                dialog.dismiss()
                onConfirmationResult(true)
                currentDialog = null
            }
            .setNegativeButton(negativeButtonText) { dialog, _ ->
                dialog.dismiss()
                onConfirmationResult(false)
                currentDialog = null
            }
            .create()

        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        currentDialog = WeakReference(dialog)
        dialog.show()

        val positiveButton: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        positiveButton.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))

        val negativeButton: Button = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
        negativeButton.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
    }

    fun showAddInfoDialog(supportFragmentManager: FragmentManager, listener: AddKeyBottomSheetDialog.AddKeyBottomSheetDialogListener) {
        val bottomSheet = AddKeyBottomSheetDialog(listener)
        bottomSheet.show(supportFragmentManager, TAG_DIALOG_ADD_INFO)
    }
}