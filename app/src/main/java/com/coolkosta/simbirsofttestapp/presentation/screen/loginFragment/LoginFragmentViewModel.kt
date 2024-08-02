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
            is LoginEvent.EmailTextChanged -> {
                _loginState.update {
                    it.copy(
                        currentEmail = loginEvent.email,
                    )
                }
                updateButtonEnabled()
            }

            is LoginEvent.PasswordTextChanged -> {
                _loginState.update {
                    it.copy(
                        currentPassword = loginEvent.password,
                    )
                }
                updateButtonEnabled()
            }
        }
    }

    private fun updateButtonEnabled() {
        _loginState.update {
            it.copy(
                isEnabled = _loginState.value.currentEmail.length >= 6 && _loginState.value.currentPassword.length >= 6
            )
        }
    }
}