package com.coolkosta.simbirsofttestapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.coolkosta.simbirsofttestapp.entity.Event
import com.coolkosta.simbirsofttestapp.entity.EventCategory
import com.coolkosta.simbirsofttestapp.util.JsonHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
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

    init {
        getEventsAsync()
        getCategoriesAsync()
        combineObservable()
    }

    private fun getEventsObservable(): Observable<List<Event>> {
        return Observable.fromCallable {
            Thread.sleep(5000)
            getApplication<Application>().assets.open("events.json").use {
                jsonHelper.getEventsFromJson(it)
            }
        }
    }

    private fun getCategoriesObservable(): Observable<List<EventCategory>> {
        return Observable.fromCallable {
            Thread.sleep(5000)
            getApplication<Application>().assets.open("categories.json").use {
                jsonHelper.getCategoryFromJson(it)
            }
        }
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

    private fun getEventsAsync() {
        getEventsObservable()
            .subscribeOn(Schedulers.io())
            .doOnNext {
                Log.d(
                    TAG,
                    "Events subscribeOn(io): ${Thread.currentThread().name}"
                )
            }
            .observeOn(Schedulers.computation())
            .doOnNext {
                Log.d(
                    TAG,
                    "Events observeOn(computation): ${Thread.currentThread().name}"
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                Log.d(
                    TAG,
                    "Events observeOn(mainThread): ${Thread.currentThread().name}"
                )
            }
            .subscribe { events ->
                _eventList.postValue(events)
                initList = events
                progress.postValue(false)
            }.isDisposed
    }

    private fun getCategoriesAsync() {
        getCategoriesObservable()
            .subscribeOn(Schedulers.newThread())
            .doOnNext {
                Log.d(
                    TAG,
                    "Categories subscribeOn(newThread): ${Thread.currentThread().name}"
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { categories ->
                filterCategories = categories.map { it.id }
            }.isDisposed
    }

    private fun combineObservable() {
        val eventsObservable = getEventsObservable()
            .subscribeOn(Schedulers.io())
            .doOnNext {
                Log.d(
                    TAG,
                    "eventsObservable on thread: ${Thread.currentThread().name}"
                )
            }
            .observeOn(Schedulers.computation())
            .doOnNext {
                Log.d(
                    TAG,
                    "eventsObservable after observeOn on thread: ${Thread.currentThread().name}"
                )
            }

        val categoriesObservable = getCategoriesObservable()
            .subscribeOn(Schedulers.io())
            .doOnNext {
                Log.d(
                    TAG,
                    "categoriesObservable on thread: ${Thread.currentThread().name}"
                )
            }
            .observeOn(Schedulers.computation())
            .doOnNext {
                Log.d(
                    TAG,
                    "categoriesObservable after observeOn on thread: ${Thread.currentThread().name}"
                )
            }

        Observable.combineLatest(eventsObservable, categoriesObservable) { events, categories ->
            val categoryMap = categories.associateBy { it.id }
            val resultMap = mutableMapOf<String, List<String>>()

            events.forEach { event ->
                event.categoryIds.forEach { categoryId ->
                    categoryMap[categoryId]?.let { category ->
                        val foundationList = resultMap.getOrPut(category.title) { mutableListOf() }
                        (foundationList as MutableList).add(event.foundation)
                    }
                }
            }
            resultMap
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { Log.d(TAG, "combineLatest after observeOn(main) on thread: ${Thread.currentThread().name}") }
            .observeOn(Schedulers.computation())
            .doOnNext { Log.d(TAG, "combineLatest after second observeOn(computation) on thread: ${Thread.currentThread().name}") }
            .subscribe({ resultMap ->
                Log.d(TAG, "Combine result $resultMap on thread: ${Thread.currentThread().name}")
            }, { error ->
                Log.e(TAG, "Error combining observables", error)
            }).isDisposed
    }

    companion object {
        private const val TAG = "NewsViewModel"
    }
}

