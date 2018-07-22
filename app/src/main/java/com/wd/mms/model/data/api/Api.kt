package com.wd.mms.model.data.api

import com.wd.mms.entity.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface Api {
    companion object {
        const val API_PATH = "ru/api/v1"
    }

    @POST("/$API_PATH/main/user/login/")
    fun signIn(@Body tokenRequest: TokenRequest): Single<Token>

    @POST("/$API_PATH/main/user/register/")
    fun signUp(@Body tokenRequest: TokenRequest): Single<Token>

    @POST("/$API_PATH/main/user/forgot_password/")
    @FormUrlEncoded
    fun forgotPassword(@Field("username") username: String): Completable

    @GET("/$API_PATH/main/template_page/")
    fun getTemplates(): Single<ArrayResponse<Template>>

    @GET("/$API_PATH/main/message/")
    fun getMessages(@Query("page") page: Int): Single<ArrayResponse<Message>>

    @GET("/$API_PATH/main/message/{id}/")
    fun getMessage(@Path("id") messageId: Int): Single<Message>

    @GET("/$API_PATH/main/subscription/")
    fun getSubscriptions(): Single<ArrayResponse<Subscription>>

    @GET("/$API_PATH/main/user/detail/")
    fun getUserDetails(): Single<User>

    @POST("/$API_PATH/payment/stripe/")
    @FormUrlEncoded
    fun createPayment(@Field("subscription") subscriptionId: Long,
                      @Field("token") stripeToken: String): Observable<Subscription>

}