package com.wd.mms.extensions

import android.content.Context
import android.support.design.card.MaterialCardView
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.wd.mms.R
import java.text.SimpleDateFormat
import java.util.*


fun View.visible(isShown: Boolean) {
            this.visibility = if (isShown) View.VISIBLE else View.GONE
        }

fun String.isValidEmail() =
        this.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun Context.toast(text: String?, length: Int = Toast.LENGTH_SHORT) =
        Toast.makeText(this, text, length).show()

fun EditText.textChangeListener(onTextChange: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            onTextChange.invoke(p0.toString())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    })

    fun MaterialCardView.isErrorEnabled(): Boolean {
        return this.strokeColor == ContextCompat.getColor(this.context, R.color.colorRed)
    }

    fun MaterialCardView.setErrorEnabled() {
        this.strokeColor = ContextCompat.getColor(this.context, R.color.colorRed)
    }

    fun String.parsedDateShortSeparator(): String {
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", resources.configuration.locale)
        val sdf: SimpleDateFormat
        val result: Date
        try {
            result = df.parse(this)
            sdf = SimpleDateFormat("yyyy-MM-dd", Locale("ru"))
            sdf.timeZone = TimeZone.getTimeZone("GMT")
        } catch (ignored: Exception) {
            return this
        }
        return sdf.format(result)
    }


}