package com.android.rickandmorty.ui.view.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.rickandmorty.data.local.AuthUiState
import com.android.rickandmorty.domain.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val authState: StateFlow<AuthUiState> = _authState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthUiState.Loading
            val result = authRepository.login(email, password)
            _authState.value = if (result.isSuccess) AuthUiState.Success else AuthUiState.Error(result.exceptionOrNull()?.message)
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthUiState.Loading
            val result = authRepository.register(email, password)
            _authState.value = if (result.isSuccess) AuthUiState.Success else AuthUiState.Error(result.exceptionOrNull()?.message)
        }
    }

    fun logout() = authRepository.logout()

    fun isUserLoggedIn(): Boolean = authRepository.getCurrentUser() != null
}
