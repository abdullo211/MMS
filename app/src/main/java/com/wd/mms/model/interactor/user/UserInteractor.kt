package com.wd.mms.model.interactor.user

import com.wd.mms.model.repository.user.UserRepository
import javax.inject.Inject

class UserInteractor @Inject constructor(private val userRepository: UserRepository) {


    fun getUserDetails() = userRepository.getSavedUserDetails()


    fun getUserSubscription() = userRepository.getActiveSubscription()
}