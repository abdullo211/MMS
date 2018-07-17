package com.wd.mms.model.data.server

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZonedDateTime
import java.lang.reflect.Type
import java.util.*

class DateDeserializer : JsonDeserializer<LocalDateTime> {
    private val offset = TimeZone.getDefault().rawOffset / 1000L

    override fun deserialize(
            json: JsonElement,
            type: Type,
            jsonDeserializationContext: JsonDeserializationContext
    ): LocalDateTime {
        return ZonedDateTime.parse(json.asJsonPrimitive.asString).plusSeconds(offset).toLocalDateTime()
    }
}
