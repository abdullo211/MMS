package com.wd.mms.ui.common

import android.support.v4.app.DialogFragment
import com.arellomobile.mvp.MvpAppCompatActivity

abstract class BaseActivity : MvpAppCompatActivity() {
    companion object {
        private val PROGRESS_TAG = "progress"
        private val SUCCESS_TAG = "success_message"
    }

    fun showProgressDialog(isLoading: Boolean) {
        val fragment = supportFragmentManager.findFragmentByTag(PROGRESS_TAG)
        if (fragment != null && !isLoading) {
            (fragment as ProgressDialog).dismissAllowingStateLoss()
            supportFragmentManager.executePendingTransactions()
        } else if (fragment == null && isLoading) {
            ProgressDialog().show(supportFragmentManager, PROGRESS_TAG)
            supportFragmentManager.executePendingTransactions()
        }
    }

    fun showSuccessMessage(message: String): DialogFragment {
        var fragment = supportFragmentManager.findFragmentByTag(SUCCESS_TAG)
        if (fragment != null) {
            supportFragmentManager.executePendingTransactions()
        } else {
            fragment = SuccessDialog()
            fragment.show(supportFragmentManager, SUCCESS_TAG)
        }
        return fragment as DialogFragment
    }
}