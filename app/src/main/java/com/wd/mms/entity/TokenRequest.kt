package com.wd.mms.entity

import com.google.gson.annotations.SerializedName
import retrofit2.http.Field

data class TokenRequest (@SerializedName("username") val email: String,
                         @SerializedName("fullname")val name: String? = null,
                         @SerializedName("password") val password: String,
                         @SerializedName("confirm_password")val confirmPassword: String = password,
                         @SerializedName("device") val device: Device)