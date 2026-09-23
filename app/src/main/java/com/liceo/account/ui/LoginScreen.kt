package com.liceo.account.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.liceo.account.ui.theme.LiceoAccountTheme

@Composable
fun LoginScreen(
    state: AuthUiState,
    onLogin: (String, String) -> Unit,
    onGoToRegister: () -> Unit
) {
    // GIVEN: what the user types lives here
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    val isLoading = state is AuthUiState.Loading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("LiceoAccount", style = MaterialTheme.typography.headlineMedium)
        Text("Log in to your account")

        // TODO 10a: OutlinedTextField for the email (label "Email")
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        // TODO 10b & TODO 13: OutlinedTextField for the password (label "Password"), hidden / show-hide toggle
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                TextButton(onClick = { showPassword = !showPassword }) {
                    Text(if (showPassword) "Hide" else "Show")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // TODO 10c: if state is AuthUiState.Error -> show state.message in red
        if (state is AuthUiState.Error) {
            Text(state.message, color = MaterialTheme.colorScheme.error)
        }

        // TODO 10d: Button "Log in" -> onLogin(email, password), disabled while loading;
        // while loading show a small CircularProgressIndicator instead of text
        Button(
            onClick = { onLogin(email, password) },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text("Log in")
            }
        }

        // TODO 10e: TextButton "No account yet? Create one" -> onGoToRegister()
        TextButton(onClick = onGoToRegister) {
            Text("No account yet? Create one")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LiceoAccountTheme {
        LoginScreen(
            state = AuthUiState.Idle,
            onLogin = { _, _ -> },
            onGoToRegister = {}
        )
    }
}

