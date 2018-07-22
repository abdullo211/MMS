package com.wd.mms.model.interactor.message

import com.wd.mms.entity.ArrayResponse
import com.wd.mms.entity.Subscription
import com.wd.mms.model.repository.message.MessageRepository
import com.wd.mms.model.repository.payment.PaymentRepository
import io.reactivex.Single
import javax.inject.Inject

class MessageInteractor @Inject constructor(private val messageRepository: MessageRepository,
                                            private val paymentRepository: PaymentRepository) {

    fun getMessages(page: Int) = messageRepository.getMessages(page)

    fun getMessage(messageId: Int) = messageRepository.getMessage(messageId)

    fun getSubscriptions(): Single<ArrayResponse<Subscription>> = paymentRepository.getSubscriptions()

}