package com.coolkosta.simbirsofttestapp.presentation.screen.newsFilterFragment

sealed interface NewsFilterSideEffect {
    data class ShowErrorToast(val message: String) : NewsFilterSideEffect
}