package com.wd.mms.model.interactor.main

import com.wd.mms.entity.User
import com.wd.mms.model.auth.AuthHolder
import com.wd.mms.model.repository.user.UserRepository
import io.reactivex.Single
import io.realm.Realm
import javax.inject.Inject

class MainInteractor @Inject constructor(private val authHolder: AuthHolder,
                                         private val realm: Realm,
                                         private val userRepository: UserRepository) {

    fun isTokenEmpty() = authHolder.token.isNullOrEmpty()

    fun clearAllUserData() {
        authHolder.clearData()

        realm.apply {
            executeTransaction { it.deleteAll() }
        }
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

    fun getTemplates() = userRepository.getTemplates()

    fun getSavedTemplates() = userRepository.getSavedTemplates()
}