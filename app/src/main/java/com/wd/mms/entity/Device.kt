package com.wd.mms.entity

import com.google.gson.annotations.SerializedName

data class Device(@SerializedName("device_id")
                  val deviceId: String? = null,
                  @SerializedName("registration_id")
                  val fcmToken: String?,
                  @SerializedName("type")
                  val deviceType: String = "android")