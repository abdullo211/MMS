package com.wd.mms.entity

import com.google.gson.annotations.SerializedName

data class Token(
        @SerializedName("token")
        val token: String,
        @SerializedName("active_subscription")
        val subscriptions: ArrayList<ActiveSubscription>? = ArrayList())