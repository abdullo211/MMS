package com.wd.mms.ui.messages.list

import android.widget.ProgressBar
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View
import com.mindorks.placeholderview.annotations.expand.Parent
import com.mindorks.placeholderview.annotations.infinite.LoadMore
import com.wd.mms.R

@Parent
@Layout(R.layout.list_item_progress)
class LoadMoreIListItem(private val onLoadMore: () -> Unit) {

    @View(R.id.progress)
    private
    lateinit var progressBar: ProgressBar


    @LoadMore
    fun onLoadMore() {
        onLoadMore.invoke()
    }
}