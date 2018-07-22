package com.wd.mms.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Subscription(@SerializedName("id")
                        val id: Long,
                        @SerializedName("title")
                        val title: String,
                        @SerializedName("description")
                        val description: String,
                        @SerializedName("amount")
                        val amount: Double,
                        @SerializedName("year")
                        val year: String,
                        @SerializedName("month")
                        val month: String,
                        @SerializedName("day")
                        val day: String):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeDouble(amount)
        parcel.writeString(year)
        parcel.writeString(month)
        parcel.writeString(day)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Subscription> {
        override fun createFromParcel(parcel: Parcel): Subscription {
            return Subscription(parcel)
        }

        override fun newArray(size: Int): Array<Subscription?> {
            return arrayOfNulls(size)
        }
    }
}