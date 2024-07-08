package com.coolkosta.simbirsofttestapp.presentation.screen.news_fragment

sealed class NewsSideEffect {
    data class ShowErrorToast(val message: String) : NewsSideEffect()
}