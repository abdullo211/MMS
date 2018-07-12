package com.wd.mms.model.data

import com.wd.mms.entity.ArrayResponse
import com.wd.mms.entity.Message
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    companion object {
        const val API_PATH = "api/v1"
    }

    @GET("/asdasd/asd")
    fun getMessages(@Query("page") page: Int): Single<ArrayResponse<Message>>

}