package com.eclipse.bot.util

import android.content.Context
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.eclipse.bot.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogUtil {
    companion object {
        fun networkUnavailableDialog(context: Context?): AlertDialog {
            val dialog = MaterialAlertDialogBuilder(context!!)
                .setTitle(R.string.networkUnavailable)
                .setMessage(R.string.checkInternet)
                .setPositiveButton(R.string.refresh, null)
                .setCancelable(false)
                .create()

            dialog.setOnShowListener {
                val button: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                button.setOnClickListener {
                    if (NetworkUtil.isNetworkAvailable(context)) {
                        dialog.dismiss()
                        return@setOnClickListener
                    }
                    Toast.makeText(context, R.string.networkUnavailable, Toast.LENGTH_SHORT).show()
                }
            }

            return dialog
        }
    }
}