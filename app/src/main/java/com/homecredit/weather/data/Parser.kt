package com.homecredit.weather.data

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object Parser {

    fun <T: Any> itemType(): Type {
        return object : TypeToken<ArrayList<T>>() {}.type
    }

}