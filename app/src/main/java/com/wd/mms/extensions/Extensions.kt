package com.wd.mms.extensions

import android.content.Context
import android.view.View
import android.widget.Toast


fun View.visible(isShown: Boolean) {
            this.visibility = if (isShown) View.VISIBLE else View.GONE
        }

fun String.isValidEmail() =
        this.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun Context.toast(text: String?, length: Int = Toast.LENGTH_SHORT) =
        Toast.makeText(this, text, length).show()