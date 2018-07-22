package com.wd.mms.ui.common

import com.arellomobile.mvp.MvpAppCompatFragment

abstract class BaseFragment :MvpAppCompatFragment() {

    companion object {
        private val PROGRESS_TAG = "progress_dialog"
    }


    protected fun showProgressDialog(progress: Boolean) {
        if (!isAdded) return

        val fragment = childFragmentManager.findFragmentByTag(PROGRESS_TAG)
        if (fragment != null && !progress) {
            (fragment as ProgressDialog).dismissAllowingStateLoss()
            childFragmentManager.executePendingTransactions()
        } else if (fragment == null && progress) {
            ProgressDialog().show(childFragmentManager, PROGRESS_TAG)
            childFragmentManager.executePendingTransactions()
        }
    }


}