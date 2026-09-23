package com.liceo.account.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liceo.account.core.AppResult
import com.liceo.account.data.UserRepository
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: UserRepository = UserRepository()
) : ViewModel() {
    // GIVEN (read it, do not change it)
    var uiState by mutableStateOf<AuthUiState>(AuthUiState.Idle)
        private set

    private val datePattern = Regex("""\d{4}-\d{2}-\d{2}""") // like 2004-05-17

    fun clearMessage() { uiState = AuthUiState.Idle }
    fun logout() { uiState = AuthUiState.Idle }

    private fun messageFor(failure: AppResult.Failure): String = when (failure) {
        AppResult.Failure.NoInternet -> "No internet connection. Please try again."
        AppResult.Failure.Timeout -> "The server was too slow. Please try again."
        AppResult.Failure.WrongLogin -> "Wrong email or password."
        AppResult.Failure.EmailTaken -> "An account with this email already exists."
        is AppResult.Failure.Unknown -> "Something went wrong: ${failure.msg}"
    }

    // TODO 8 login() — check the input, then ask the repository (7 points)
    fun login(email: String, password: String) {
        // TODO 8a: if email or password is blank -> set uiState to an Error
        // with the login message from the table, then return
        if (email.isBlank() || password.isBlank()) {
            uiState = AuthUiState.Error("Please enter your email and password.")
            return
        }
        // TODO 8b: set uiState to Loading
        uiState = AuthUiState.Loading
        // TODO 8c: launch a coroutine, call repository.login(email, password)
        // and turn the result into a state:
        // Success -> AuthUiState.LoggedIn(result.data)
        // Failure -> AuthUiState.Error(messageFor(result))
        viewModelScope.launch {
            uiState = when (val result = repository.login(email, password)) {
                is AppResult.Success -> AuthUiState.LoggedIn(result.data)
                is AppResult.Failure -> AuthUiState.Error(messageFor(result))
            }
        }
    }

    // TODO 9 register() — check four fields, then ask the repository (9 points)
    fun register(fullName: String, email: String, password: String, birthdate: String) {
        // TODO 9a: any of the four is blank -> Error, then return
        if (fullName.isBlank() || email.isBlank() || password.isBlank() || birthdate.isBlank()) {
            uiState = AuthUiState.Error("Please fill in all four fields.")
            return
        }
        // TODO 9b: email does not contain "@" -> Error, then return
        if (!email.contains("@")) {
            uiState = AuthUiState.Error("Please enter a valid email.")
            return
        }
        // TODO 9c: password is shorter than 6 -> Error, then return
        if (password.length < 6) {
            uiState = AuthUiState.Error("Password must be at least 6 characters.")
            return
        }
        // TODO 9d: birthdate does not match datePattern -> Error, then return
        if (!datePattern.matches(birthdate.trim())) {
            uiState = AuthUiState.Error("Birthdate must look like 2004-05-17.")
            return
        }
        // TODO 9e: Loading, then launch and call repository.register(...)
        // Success -> AuthUiState.AccountCreated(result.data.fullName)
        // Failure -> AuthUiState.Error(messageFor(result))
        uiState = AuthUiState.Loading
        viewModelScope.launch {
            uiState = when (val result = repository.register(fullName, email, password, birthdate)) {
                is AppResult.Success -> AuthUiState.AccountCreated(result.data.fullName)
                is AppResult.Failure -> AuthUiState.Error(messageFor(result))
            }
        }
    }
}
