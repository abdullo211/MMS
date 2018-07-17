package com.wd.mms.model.interactor.main

import com.wd.mms.entity.User
import com.wd.mms.model.auth.AuthHolder
import com.wd.mms.model.repository.user.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class MainInteractor @Inject constructor(private val authHolder: AuthHolder,
                                         private val userRepository: UserRepository) {

    fun isTokenEmpty() = authHolder.token.isNullOrEmpty()

    fun clearAllUserData() {
        authHolder.clearData()
    }

    fun isUserDetailsEmpty(): Boolean =
            authHolder.userName.isNullOrBlank()

    fun getUserDetails(): Single<User> =
            userRepository.getUserDetails()
                    .doAfterSuccess { saveUserDetails(it) }

    private fun saveUserDetails(user: User?) {
        user?.let {
            authHolder.userName = it.username
            authHolder.fullName = it.fullname
        }
    }
}