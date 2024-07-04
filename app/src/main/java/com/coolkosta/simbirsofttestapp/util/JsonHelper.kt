package com.coolkosta.simbirsofttestapp.util


import com.coolkosta.simbirsofttestapp.data.source.local.entity.CategoryEntity
import com.coolkosta.simbirsofttestapp.data.source.local.entity.EventEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream
import java.io.InputStreamReader

class JsonHelper {
    fun getEventsFromJson(inputStream: InputStream): List<EventEntity> {
        val reader = InputStreamReader(inputStream)
        val type = object : TypeToken<List<EventEntity>>() {}.type
        return Gson().fromJson(reader, type)
    }

    fun getCategoryFromJson(inputStream: InputStream): List<CategoryEntity>{
        val reader = InputStreamReader(inputStream)
        val type = object: TypeToken<List<CategoryEntity>>(){}.type
        return Gson().fromJson(reader,type)
    }
}