package com.coolkosta.simbirsofttestapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.coolkosta.simbirsofttestapp.data.source.local.model.CategoryDbModel

import com.coolkosta.simbirsofttestapp.util.JsonHelper

class NewsFilterViewModel(application: Application) : AndroidViewModel(application) {

    private val jsonHelper = JsonHelper()

    private val _categories = MutableLiveData<List<CategoryDbModel>>()
    val categories: LiveData<List<CategoryDbModel>> get() = _categories

    var filterCategories: MutableList<Int> = mutableListOf()

    init {
        _categories.value = getCategories()
    }

    fun initFilterCategories(categories: List<Int>) {
        filterCategories = categories.toMutableList()
    }

    private fun getCategories(): List<CategoryDbModel> {
        getApplication<Application>().assets.open("categories.json").use {
            return jsonHelper.getCategoryFromJson(it)
        }
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