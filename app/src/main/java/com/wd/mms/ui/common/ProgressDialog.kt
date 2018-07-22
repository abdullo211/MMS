package com.wd.mms.ui.common

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatDialogFragment
import com.wd.mms.R

class ProgressDialog : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false
        return android.support.v7.app.AlertDialog.Builder(context!!)
                .setView(R.layout.dialog_progress)
                .create()
    }
}