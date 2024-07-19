package com.coolkosta.profile.di

fun interface ProfileComponentProvider {
    fun getProfileComponent(): ProfileComponent
}