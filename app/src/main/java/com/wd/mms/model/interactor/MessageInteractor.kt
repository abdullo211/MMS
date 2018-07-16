package com.wd.mms.model.interactor

import com.wd.mms.model.repository.message.MessageRepository
import javax.inject.Inject

class MessageInteractor @Inject constructor(private val messageRepository: MessageRepository) {

    fun getMessages(page: Int) = messageRepository.getMessages(page)

}