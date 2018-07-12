package com.wd.mms.extensions

import android.view.View


        fun View.visible(isShown: Boolean) {
            this.visibility = if (isShown) View.VISIBLE else View.GONE
        }
