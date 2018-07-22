package com.wd.mms.model.interactor.payment

import com.stripe.android.model.Card
import com.wd.mms.entity.Subscription
import com.wd.mms.model.repository.payment.PaymentRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PaymentInteractor @Inject constructor(private val paymentRepository: PaymentRepository) {

    fun createPayment(subscriptionId: Long, card: Card): Observable<Subscription> {
        return paymentRepository.createToken(card).flatMap { token ->
            return@flatMap paymentRepository.createPayment(subscriptionId, token.id)
        }.subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())

    }
}