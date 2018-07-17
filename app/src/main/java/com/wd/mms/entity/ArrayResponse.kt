package com.wd.mms.entity

import com.google.gson.annotations.SerializedName

data class ArrayResponse<T : Any>(@SerializedName("results")
                                  val list: ArrayList<T> = ArrayList(),
                                  @SerializedName("next")
                                  val nextPage: String? = null
)
