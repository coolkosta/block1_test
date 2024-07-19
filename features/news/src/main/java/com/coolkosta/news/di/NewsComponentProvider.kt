package com.coolkosta.news.di

fun interface NewsComponentProvider {
    fun getNewsComponent(): NewsComponent
}