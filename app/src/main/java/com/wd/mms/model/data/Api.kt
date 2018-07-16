package com.wd.mms.model.data

import com.wd.mms.entity.ArrayResponse
import com.wd.mms.entity.Message
import com.wd.mms.entity.Token
import io.reactivex.Single
import retrofit2.http.*

interface Api {
    companion object {
        const val API_PATH = "ru/api/v1"
    }

    @POST("/$API_PATH/main/user/login/")
    @FormUrlEncoded
    fun signIn(@Field("username") login: String,
               @Field("password") password: String): Single<Token>

    @POST("/$API_PATH/main/user/register/")
    @FormUrlEncoded
    fun signUp(@Field("username") email: String,
               @Field("fullname") name: String,
               @Field("password") password: String,
               @Field("confirm_password") confirmPassword: String): Single<Token>

    @GET("/$API_PATH/main/message/")
    fun getMessages(@Query("page") page: Int): Single<ArrayResponse<Message>>

}