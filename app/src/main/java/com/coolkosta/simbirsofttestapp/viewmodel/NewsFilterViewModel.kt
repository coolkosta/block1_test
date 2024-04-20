package com.coolkosta.simbirsofttestapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.coolkosta.simbirsofttestapp.util.EventCategory
import com.coolkosta.simbirsofttestapp.util.JsonHelper

class NewsFilterViewModel(application: Application) : AndroidViewModel(application) {

    private val jsonHelper = JsonHelper()

    private val _filteredList = MutableLiveData<List<Int>>()
    val filteredList: LiveData<List<Int>> get() = _filteredList

    private val _categories = MutableLiveData<List<EventCategory>>()
    val categories: LiveData<List<EventCategory>> get() = _categories

    init {
        getCategories()
        _filteredList.value = _categories.value?.map { it.id } as MutableList<Int>
    }

    private fun getCategories() {
        val inputStream = getApplication<Application>().assets.open("categories.json")
        _categories.value = jsonHelper.getCategoryFromJson(inputStream)
    }

    fun onSwitchChanged(idCategory: Int, isSwitchEnable: Boolean) {
        val currentFilteredCategories = _filteredList.value?.toMutableList()
        if (isSwitchEnable) {
            if (currentFilteredCategories?.contains(idCategory) != true) {
                currentFilteredCategories?.add(idCategory)
            }
        } else {
            currentFilteredCategories?.remove(idCategory)
        }
        _filteredList.value = currentFilteredCategories ?: emptyList()
    }
}