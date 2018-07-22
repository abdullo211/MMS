package com.wd.mms.ui.messages.list

import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import com.mindorks.placeholderview.annotations.Click
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import com.mindorks.placeholderview.annotations.expand.Parent
import com.wd.mms.R
import com.wd.mms.entity.Message
import java.text.SimpleDateFormat
import java.util.*

@Parent
@Layout(R.layout.list_item_message)
class MessageListItem(private val message: Message, private val onSeeMoreClick: (Message) -> Unit) {

    @View(R.id.messageDateText)
    lateinit var dateText: AppCompatTextView

    @View(R.id.messageDescriptionText)
    lateinit var descriptionText: AppCompatTextView

    @View(R.id.messageIconImage)
    lateinit var iconImage: AppCompatImageView

    @Resolve
    fun onResolve() {
        descriptionText.text = message.description
        dateText.text = message.createdDate?.parsedDateShortSeparator()
        iconImage.setImageResource(getStatusImage(message.type))
    }

    @Click(R.id.messageItemMainLayout)
    fun onSeeMoreClicked() {
        onSeeMoreClick.invoke(message)
    }

    fun String.parsedDateShortSeparator(): String {
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.ENGLISH)
        val sdf: SimpleDateFormat
        val result: Date
        try {
            result = df.parse(this)
            sdf = SimpleDateFormat("yyyy-MM-dd", Locale("ru"))
            sdf.timeZone = TimeZone.getTimeZone("GMT")
        } catch (ignored: Exception) {
            ignored.printStackTrace()
            return this
        }
        return sdf.format(result)
    }

    private fun getStatusImage(type: String): Int {
        return when (type) {
            "warning" -> R.drawable.ic_report_problem_yellow_36dp
            "up" -> R.drawable.ic_arrow_upward_green_36dp
            "down" -> R.drawable.ic_arrow_downward_red_36dp
            else -> {
                R.drawable.ic_report_problem_yellow_36dp
            }
        }
    }
}