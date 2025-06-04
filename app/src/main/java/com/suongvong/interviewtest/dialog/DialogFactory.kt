package com.suongvong.interviewtest.dialog

import android.content.Context
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.suongvong.interviewtest.R
import com.suongvong.interviewtest.constants.TAG_DIALOG_ADD_INFO
import com.suongvong.interviewtest.model.LanguageItem
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


     fun openLanguageDialog(context: Context?, selectedLanguageItem: (LanguageItem)->Unit) {
         context?.let {


             val languages = listOf(
//            LanguageItem("ar", "Arabic", R.drawable.flag_ar),
//            LanguageItem("de", "German", R.drawable.flag_de),
//            LanguageItem("en", "English", R.drawable.flag_en),
//            LanguageItem("es", "Spanish", R.drawable.flag_es),
//            LanguageItem("fr", "French", R.drawable.flag_fr),
//            LanguageItem("he", "Hebrew", R.drawable.flag_he),
//            LanguageItem("it", "Italian", R.drawable.flag_it),
//            LanguageItem("nl", "Dutch", R.drawable.flag_nl),
//            LanguageItem("no", "Norwegian", R.drawable.flag_no),
//            LanguageItem("pt", "Portuguese", R.drawable.flag_pt),
//            LanguageItem("ru", "Russian", R.drawable.flag_ru),
//            LanguageItem("sv", "Swedish", R.drawable.flag_sv),
//            LanguageItem("ud", "Urdu", R.drawable.flag_ud),
//            LanguageItem("zh", "Chinese", R.drawable.flag_zh),

                 LanguageItem("ar", "Arabic", R.drawable.ic_delete),
                 LanguageItem("de", "German", R.drawable.ic_delete),
                 LanguageItem("en", "English", R.drawable.ic_delete),
                 LanguageItem("es", "Spanish", R.drawable.ic_delete),
                 LanguageItem("fr", "French", R.drawable.ic_delete),
                 LanguageItem("he", "Hebrew", R.drawable.ic_delete),
                 LanguageItem("it", "Italian", R.drawable.ic_delete),
                 LanguageItem("nl", "Dutch", R.drawable.ic_delete),
                 LanguageItem("no", "Norwegian", R.drawable.ic_delete),
                 LanguageItem("pt", "Portuguese", R.drawable.ic_delete),
                 LanguageItem("ru", "Russian", R.drawable.ic_delete),
                 LanguageItem("sv", "Swedish", R.drawable.ic_delete),
                 LanguageItem("ud", "Urdu", R.drawable.ic_delete),
                 LanguageItem("zh", "Chinese", R.drawable.ic_delete),
             )

             LanguageDialog(context, languages) { languageItem ->
                 selectedLanguageItem.invoke(languageItem)
             }.show()
         }
    }
}