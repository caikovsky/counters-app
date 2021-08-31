package com.cornershop.counterstest.util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

data class DialogButton(val name: String, val action: DialogInterface.OnClickListener)

object DialogUtil {

    fun getDialog(
        context: Context,
        title: String,
        message: String,
        isCancelable: Boolean = false,
        dialogButton: DialogButton,
        negativeButton: DialogButton? = null
    ): AlertDialog {
        val dialogBuilder = AlertDialog.Builder(context)

        negativeButton?.let {
            dialogBuilder.setPositiveButton(dialogButton.name, dialogButton.action)
            dialogBuilder.setNegativeButton(negativeButton.name, negativeButton.action)
        } ?: run {
            dialogBuilder.setNeutralButton(dialogButton.name, dialogButton.action)
        }

        return dialogBuilder.apply {
            setTitle(title)
            setMessage(message)
            setCancelable(isCancelable)
        }.create()
    }
}