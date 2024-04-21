package com.coolkosta.simbirsofttestapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.coolkosta.simbirsofttestapp.util.EventCategory
import com.coolkosta.simbirsofttestapp.util.JsonHelper

class NewsFilterViewModel(application: Application) : AndroidViewModel(application) {

    private val jsonHelper = JsonHelper()

    private val _categories = MutableLiveData<List<EventCategory>>()
    val categories: LiveData<List<EventCategory>> get() = _categories

    var filterCategories: MutableList<Int> = mutableListOf()

    init {
        getCategories()
    }

    fun initFilterCategories(categories: List<Int>) {
        filterCategories = categories.toMutableList()
    }

    private fun getCategories() {
        val inputStream = getApplication<Application>().assets.open("categories.json")
        _categories.value = jsonHelper.getCategoryFromJson(inputStream)
    }

    fun onSwitchChanged(idCategory: Int, isSwitchEnable: Boolean) {
        if (isSwitchEnable) {
            if (!filterCategories.contains(idCategory)) {
                filterCategories.add(idCategory)
            }
        } else {
            filterCategories.remove(idCategory)
        }
    }
}