package com.coolkosta.simbirsofttestapp.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream
import java.io.InputStreamReader

class JsonHelper {
    fun getEventsFromJson(inputStream: InputStream): List<Event> {
        val reader = InputStreamReader(inputStream)
        val type = object : TypeToken<List<Event>>() {}.type
        return Gson().fromJson(reader, type)
    }

    fun getCategoryFromJson(inputStream: InputStream): List<EventCategory>{
        val reader = InputStreamReader(inputStream)
        val type = object: TypeToken<List<EventCategory>>(){}.type
        return Gson().fromJson(reader,type)
    }
}