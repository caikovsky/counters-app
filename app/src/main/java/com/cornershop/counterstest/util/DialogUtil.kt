package com.cornershop.counterstest.util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.cornershop.counterstest.R

data class DialogButton(val text: String, val action: DialogInterface.OnClickListener)

object DialogUtil {

    fun getDialog(
        context: Context,
        title: String,
        message: String,
        isCancelable: Boolean = false,
        dialogButton: DialogButton,
        negativeButton: DialogButton? = null
    ): AlertDialog {
        val dialogBuilder = AlertDialog.Builder(context, R.style.AlertDialogTheme)

        negativeButton?.let {
            dialogBuilder.setPositiveButton(dialogButton.text, dialogButton.action)
            dialogBuilder.setNegativeButton(negativeButton.text, negativeButton.action)
        } ?: run {
            dialogBuilder.setNegativeButton(dialogButton.text, dialogButton.action)
        }

        return dialogBuilder.apply {
            setTitle(title)
            setMessage(message)
            setCancelable(isCancelable)
        }.create()
    }
}