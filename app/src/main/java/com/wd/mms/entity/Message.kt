package com.wd.mms.entity

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

data class Message(
        @SerializedName("id")
        val id: Int,
        @SerializedName("description")
        val description: String,
        @SerializedName("created_date")
        val createdDate: String?,
        @SerializedName("message")
        val message: String?,
        @SerializedName("type")
        val type: String,
        @SerializedName("image")
        val image: String?)