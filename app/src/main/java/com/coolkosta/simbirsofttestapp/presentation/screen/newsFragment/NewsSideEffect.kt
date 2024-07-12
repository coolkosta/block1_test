package com.coolkosta.simbirsofttestapp.presentation.screen.newsFragment

sealed interface NewsSideEffect {
    data class ShowErrorToast(val message: String) : NewsSideEffect
}