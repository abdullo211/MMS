package com.wd.mms.entity

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Template(@PrimaryKey
                    @SerializedName("id")
                    var id: Int,
                    @SerializedName("codename")
                    var code: String,
                    @SerializedName("title")
                    var title: String,
                    @SerializedName("body")
                    var body: String) : RealmObject() {
    constructor() : this(1, "", "", "")
}