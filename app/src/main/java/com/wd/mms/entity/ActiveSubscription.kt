package com.wd.mms.entity

import com.google.gson.annotations.SerializedName

data class ActiveSubscription(@SerializedName("subscription")
                              val subscription: Subscription,
                              @SerializedName("begin_date")
                              val beginDate: String,
                              @SerializedName("end_date")
                              val endDate: String
)