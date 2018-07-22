package com.wd.mms.ui.common

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wd.mms.R
import io.reactivex.Completable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class SuccessDialog : DialogFragment() {

    private var completable: Disposable? = null

    companion object {
        const val DIALOG_MESSAGE = "success_message"
        const val DIALOG_ICON = "success_icon"
    }

    private val successMessage: String?
        get() =
            arguments?.getString(DIALOG_MESSAGE, getString(R.string.message_operation_finishsuccess))

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_success, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = true
        startFinishTick()
    }

    private fun startFinishTick() {

    }

    override fun onDestroy() {
        super.onDestroy()
        completable?.dispose()
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        finish()
    }

    private fun finish() {
        if (activity is DialogInterface.OnDismissListener)
            (activity as DialogInterface.OnDismissListener).onDismiss(dialog)
    }

}