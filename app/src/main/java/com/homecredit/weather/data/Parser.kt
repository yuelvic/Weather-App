package com.homecredit.weather.data

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object Parser {

    inline fun <reified T> itemType(): Type {
        return object : TypeToken<ArrayList<T>>() {}.type
    }

}