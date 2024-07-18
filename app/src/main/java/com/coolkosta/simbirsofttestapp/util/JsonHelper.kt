package com.coolkosta.simbirsofttestapp.util


import com.coolkosta.news.data.source.local.model.CategoryDbModel
import com.coolkosta.news.data.source.local.model.EventDbModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream
import java.io.InputStreamReader

class JsonHelper {
    fun getEventsFromJson(inputStream: InputStream): List<EventDbModel> {
        val reader = InputStreamReader(inputStream)
        val type = object : TypeToken<List<EventDbModel>>() {}.type
        return Gson().fromJson(reader, type)
    }

    fun getCategoryFromJson(inputStream: InputStream): List<CategoryDbModel> {
        val reader = InputStreamReader(inputStream)
        val type = object : TypeToken<List<CategoryDbModel>>() {}.type
        return Gson().fromJson(reader, type)
    }
}