package com.wd.mms.model.system

import android.content.Context
import android.support.v4.content.ContextCompat
import javax.inject.Inject

class ResourceManager @Inject constructor(private val context: Context) {

    fun getString(id: Int) = context.getString(id)

    fun getColor(id:Int) = ContextCompat.getColor(context,id)
}