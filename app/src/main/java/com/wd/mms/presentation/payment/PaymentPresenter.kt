package com.wd.mms.presentation.payment

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.stripe.android.model.Card
import com.wd.mms.R
import com.wd.mms.model.interactor.payment.PaymentInteractor
import com.wd.mms.model.system.ResourceManager
import com.wd.mms.presentation.base.BasePresenter
import com.wd.mms.toothpick.PrimitiveWrapper
import com.wd.mms.toothpick.qualifier.SubscriptionId
import javax.inject.Inject

@InjectViewState
class PaymentPresenter @Inject constructor(
        val paymentInteractor: PaymentInteractor,
        val resourceManager: ResourceManager,
        @SubscriptionId private val subscription: PrimitiveWrapper<Long>)
    : BasePresenter<PaymentView>() {


    fun onPaymentClicked(cardNumber: String, month: String, year: String, cvc: String) {
        val card = Card(cardNumber, month.toIntOrNull(), year.toIntOrNull(), cvc)
        if (validateCard(card))
            createPayment(card)
    }

    private fun createPayment(card: Card) {
        paymentInteractor.createPayment(subscription.value, card)
                .doOnSubscribe { viewState.showProgress(true) }
                .doFinally { viewState.showProgress(false) }
                .subscribe({
                    viewState.showSuccessMessage()
                }, {
                    it.printStackTrace()
                    viewState.showError(resourceManager.getString(R.string.error_operation))
                }).apply {
                    connect()
                }
    }

    private fun validateCard(card: Card): Boolean {
        var isValid = true
        if (!card.validateNumber()) {
            isValid = false
            viewState.showCardNumberError()
        }

        if (!card.validateExpMonth()) {
            isValid = false
            viewState.showExpMonthError()
        }

        if (!card.validateExpiryDate()) {
            isValid = false
            viewState.showExpYearError()
        }

        if (!card.validateCVC()) {
            viewState.showCVCError()
        }
        return isValid && card.validateCard()
    }
}