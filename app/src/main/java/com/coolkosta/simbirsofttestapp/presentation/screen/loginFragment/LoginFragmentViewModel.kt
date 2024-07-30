package com.coolkosta.simbirsofttestapp.presentation.screen.loginFragment

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class LoginFragmentViewModel @Inject constructor() : ViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    fun sendEvent(loginEvent: LoginEvent) {
        when (loginEvent) {
            is LoginEvent.EmailTextChanged -> _loginState.update {
                it.copy(currentEmail = loginEvent.email)
            }

            is LoginEvent.PasswordTextChanged -> _loginState.update {
                it.copy(currentPassword = loginEvent.password)
            }
        }
    }
}