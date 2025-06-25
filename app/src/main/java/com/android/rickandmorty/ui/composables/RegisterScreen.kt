package com.android.rickandmorty.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.rickandmorty.data.local.AuthUiState
import com.android.rickandmorty.ui.theme.RickAndMortyTheme
import com.android.rickandmorty.ui.view.viewModel.AuthViewModel

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var registerData by remember { mutableStateOf(RegisterData()) }
    var localErrorMessage by remember { mutableStateOf<String?>(null) }

    val authState by viewModel.authState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Registrarse",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp),
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = registerData.email,
            onValueChange = { registerData = registerData.copy(email = it) },
            label = { Text("Correo electrónico") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = registerData.password,
            onValueChange = { registerData = registerData.copy(password = it) },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = registerData.confirmPassword,
            onValueChange = { registerData = registerData.copy(confirmPassword = it) },
            label = { Text("Confirmar contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        localErrorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (authState is AuthUiState.Error) {
            val error = (authState as AuthUiState.Error).message
            Text(text = error ?: "Error desconocido", color = MaterialTheme.colorScheme.error)
        }

        Button(
            onClick = {
                val validationError = registerData.validationMessage()
                if (validationError != null) {
                    localErrorMessage = validationError
                } else {
                    localErrorMessage = null
                    viewModel.register(
                        registerData.email.trim(),
                        registerData.password.trim()
                    )
                }
            },
            enabled = authState !is AuthUiState.Loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (authState is AuthUiState.Loading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text("Crear cuenta")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(onClick = onNavigateToLogin) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }

    if (authState is AuthUiState.Success) {
        LaunchedEffect(Unit) {
            onRegisterSuccess()
        }
    }
}


@Preview
@Composable
private fun RegisterScreenPreview() {
    RickAndMortyTheme {
        RegisterScreen(
            onRegisterSuccess = {}, onNavigateToLogin = {}
        )
    }
}

data class RegisterData(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = ""
) {
    fun isValid(): Boolean {
        return email.isNotBlank()
                && password.isNotBlank()
                && confirmPassword.isNotBlank()
                && password == confirmPassword
    }

    fun validationMessage(): String? {
        return when {
            email.isBlank() -> "El correo es obligatorio"
            password.isBlank() -> "La contraseña es obligatoria"
            confirmPassword.isBlank() -> "Confirma tu contraseña"
            password != confirmPassword -> "Las contraseñas no coinciden"
            else -> null
        }
    }
}


