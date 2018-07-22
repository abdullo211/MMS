package com.wd.mms.ui.user

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.wd.mms.R
import com.wd.mms.entity.ActiveSubscription
import com.wd.mms.extensions.*
import com.wd.mms.model.interactor.user.UserInteractor
import com.wd.mms.model.repository.user.UserRepository
import com.wd.mms.presentation.user.UserPresenter
import com.wd.mms.presentation.user.UserView
import com.wd.mms.toothpick.DI
import com.wd.mms.ui.common.BaseActivity
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.list_item_subscription.*
import toothpick.Toothpick
import toothpick.config.Module
import java.text.SimpleDateFormat

import java.util.*

class UserActivity : BaseActivity(), UserView {


    @InjectPresenter
    lateinit var presenter: UserPresenter

    @ProvidePresenter
    fun provide(): UserPresenter {
        return Toothpick.openScopes(DI.SERVER_SCOPE, DI.USER_SCOPE)
                .apply {
                    installModules(object : Module() {
                        init {
                            bind(UserRepository::class.java)
                            bind(UserInteractor::class.java)
                        }
                    })
                }
                .getInstance(UserPresenter::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this, Toothpick.openScopes(DI.USER_SCOPE))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        toolbar.setNavigationOnClickListener { finish() }
    }

    override fun setUserName(username: String?) {
        userFirstLetter.text = username?.firstOrNull()?.toUpperCase()?.toString()
        userNameText.text = username
    }

    override fun setUserFullName(fullname: String?) {
        userFullName.text = "User name: $fullname"
    }

    override fun showNoSubscriptionText() {
        noSubscriptionText.visible(true)
    }

    override fun showSubscription(subscription: ActiveSubscription) {
        subscriptionTitle.text = subscription.subscription.title
        subscriptionAmount.text = "End of subscription : " +  subscription.endDate.parsedDateShortSeparator()

    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing)
            Toothpick.closeScope(DI.USER_SCOPE)
    }


    fun String.parsedDateShortSeparator(): String {
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", resources.configuration.locale)
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


}