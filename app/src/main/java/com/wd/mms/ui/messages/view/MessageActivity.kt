package com.wd.mms.ui.messages.view

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import com.wd.mms.R
import com.wd.mms.entity.Message
import com.wd.mms.extensions.visible
import com.wd.mms.presentation.messages.info.MessageInfoPresenter
import com.wd.mms.presentation.messages.info.MessageInfoView
import com.wd.mms.toothpick.DI
import com.wd.mms.toothpick.PrimitiveWrapper
import com.wd.mms.toothpick.qualifier.MessageId
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.content_message.*
import toothpick.Toothpick
import toothpick.config.Module
import java.text.SimpleDateFormat
import java.util.*

class MessageActivity : MvpAppCompatActivity(), MessageInfoView {


    @InjectPresenter
    lateinit var presenter: MessageInfoPresenter

    private val message: Int? get() = intent.getIntExtra("message", -1)

    @ProvidePresenter
    fun provide(): MessageInfoPresenter {
        return Toothpick.openScopes(DI.SERVER_SCOPE, DI.MESSAGE_SCOPE)
                .apply {
                    installModules(object : Module() {
                        init {
                            bind(PrimitiveWrapper::class.java)
                                    .withName(MessageId::class.java)
                                    .toInstance(PrimitiveWrapper(message))
                        }
                    })
                }
                .getInstance(MessageInfoPresenter::class.java)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this, Toothpick.openScope(DI.SERVER_SCOPE))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        toolbar.setSubtitleTextColor(Color.BLACK)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }


    override fun showMessageDetails(message: Message) {
        message.message?.let {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                messageText.text = Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY)
            } else {
                messageText.text = Html.fromHtml(it)
            }
        }
        message.image?.let {
            Glide.with(this).load(it).into(messageImage)
        }
        iconImage.setImageResource(getStatusImage(message.type))
        descriptionText.text = message.description
        dateText.text = message.createdDate?.parsedDateShortSeparator()
    }

    override fun showProgress(isLoading: Boolean) {
        messageProgress.visible(isLoading)
    }


    fun String.parsedDateShortSeparator(): String {
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.ENGLISH)
        val sdf: SimpleDateFormat
        val result: Date
        try {
            result = df.parse(this)
            sdf = SimpleDateFormat("yyyy-MM-dd hh:mm", Locale("ru"))
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

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing)
            Toothpick.closeScope(DI.MESSAGE_SCOPE)
    }

}