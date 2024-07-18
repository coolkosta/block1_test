package com.coolkosta.news.presentation.screen.newsFragment

sealed interface NewsSideEffect {
    data class ShowErrorToast(val message: String) : NewsSideEffect
}