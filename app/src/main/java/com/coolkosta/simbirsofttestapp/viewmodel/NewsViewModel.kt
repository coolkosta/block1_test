package com.coolkosta.simbirsofttestapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.coolkosta.simbirsofttestapp.api.RetrofitProvider
import com.coolkosta.simbirsofttestapp.entity.Event
import com.coolkosta.simbirsofttestapp.util.CategoryMapper
import com.coolkosta.simbirsofttestapp.util.EventFlow
import com.coolkosta.simbirsofttestapp.util.EventMapper
import com.coolkosta.simbirsofttestapp.util.JsonHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class NewsViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private val jsonHelper = JsonHelper()
    private var _eventList = MutableLiveData<List<Event>>()
    val eventList: LiveData<List<Event>> get() = _eventList

    val progress = MutableLiveData(true)

    var filterCategories: List<Int> = listOf()
    private var initList: List<Event> = listOf()

    private var unreadCount: Int = 0
    private val readEvents: MutableList<Int> = mutableListOf()

    private val disposables = CompositeDisposable()

    init {
        getCategories()
        getEvents()
    }

    private fun getEvents() {
        RetrofitProvider.eventsApi.getEvents()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { remoteEvents ->
                remoteEvents.map {
                    EventMapper.fromRemoteEventToEvent(it)
                }
            }
            .onErrorResumeNext {
                Observable.fromCallable {
                    Thread.sleep(TIMEOUT)
                    getApplication<Application>().assets.open("events.json").use {
                        jsonHelper.getEventsFromJson(it)
                    }
                }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
            .subscribe(
                { eventList ->
                    _eventList.value = eventList
                    initList = eventList
                    unreadCount = eventList.size
                    fetchUnreadCount(eventList.size)
                    progress.value = false
                },
                { error ->
                    Log.e(EXCEPTION_TAG, "Error events observable", error)
                }
            )
            .addTo(disposables)
    }

    private fun getCategories() {
        RetrofitProvider.categoriesApi.getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { remoteCategories ->
                remoteCategories.map {
                    CategoryMapper.fromCategoryToEventCategory(it.value)
                }
            }
            .onErrorResumeNext {
                Observable.fromCallable {
                    Thread.sleep(TIMEOUT)
                    getApplication<Application>().assets.open("categories.json").use {
                        jsonHelper.getCategoryFromJson(it)
                    }
                }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            }
            .subscribe(
                { categories ->
                    filterCategories = categories.map { it.id }
                },
                { error ->
                    Log.e(EXCEPTION_TAG, "Error categories observable", error)
                }
            )
            .addTo(disposables)
    }

    fun onCategoriesChanged(categories: List<Int>) {
        filterCategories = categories
        filteredList()
    }

    private fun filteredList() {
        _eventList.value = initList.filter { event ->
            filterCategories.any {
                event.categoryIds.contains(it)
            }
        }
    }

    fun readEvent(event: Event) {
        if (!readEvents.contains(event.id)) {
            unreadCount--
            readEvents.add(event.id)
            fetchUnreadCount(unreadCount)
        }
    }

    private fun fetchUnreadCount(unreadCount: Int) {
        EventFlow.publish(unreadCount)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    companion object {
        private const val TIMEOUT = 5000L
        private const val EXCEPTION_TAG = "NewsViewModel"
    }
}

