package com.wd.mms.ui.messages.list

import android.support.v7.widget.AppCompatTextView
import com.mindorks.placeholderview.annotations.Click
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.View
import com.wd.mms.R
import com.wd.mms.entity.Message

@Layout(R.layout.list_item_message)
class MessageListItem(private val message: Message, private val onSeeMoreClick: (Int) -> Unit) {

    @View(R.id.messageDateText)
    lateinit var dateText: AppCompatTextView

    @View(R.id.messageDescriptionText)
    lateinit var descriptionText: AppCompatTextView

    @View(R.id.messageIconImage)
    lateinit var iconImage: AppCompatTextView


    @Click(R.id.messageSeeMoreText)
    fun OnSeeMoreClicked() {
        onSeeMoreClick.invoke(message.id)
    }
}