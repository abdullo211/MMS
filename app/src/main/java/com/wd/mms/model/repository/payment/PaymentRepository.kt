package com.wd.mms.model.repository.payment

import android.content.Context
import com.stripe.android.Stripe
import com.stripe.android.TokenCallback
import com.stripe.android.model.Card
import com.stripe.android.model.Token
import com.wd.mms.R
import com.wd.mms.entity.ArrayResponse
import com.wd.mms.entity.Subscription
import com.wd.mms.model.data.api.Api
import com.wd.mms.model.system.ResourceManager
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import javax.inject.Inject

class PaymentRepository @Inject constructor(
        private val context: Context,
        private val api: Api,
        private val resourceManager: ResourceManager) {

    fun getSubscriptions(): Single<ArrayResponse<Subscription>> =
            api.getSubscriptions()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

    fun createPayment(subscriptionId: Long, stripeToken: String): Observable<Subscription> {
        return api.createPayment(subscriptionId, stripeToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun createToken(card: Card): Observable<Token> {
        return Observable.create { single ->
            Stripe(context, resourceManager.getString(R.string.stripe_key))
                    .createToken(
                            card,
                            object : TokenCallback {
                                override fun onSuccess(token: Token?) {
                                    single.onNext(token!!)
                                    single.onComplete()
                                }

                                override fun onError(error: Exception?) {
                                    single.onError(error!!.fillInStackTrace())
                                }
                            })
        }
    }




}