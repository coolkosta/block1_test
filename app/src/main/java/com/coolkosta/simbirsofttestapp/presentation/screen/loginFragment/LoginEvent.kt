package com.coolkosta.simbirsofttestapp.presentation.screen.loginFragment

sealed interface LoginEvent {
    data class EmailTextChanged(val email: String) : LoginEvent
    data class PasswordTextChanged(val password: String) : LoginEvent
    data object LoginButtonEnabled: LoginEvent
}