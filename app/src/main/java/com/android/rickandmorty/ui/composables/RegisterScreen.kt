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
import com.android.rickandmorty.ui.theme.RickAndMortyTheme

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    register: (email: String, password: String, callback: (Boolean, String?) -> Unit) -> Unit
) {
    var registerData by remember { mutableStateOf(RegisterData()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Registrarse",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
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

        errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Button(
            onClick = {
                val validationError = registerData.validationMessage()
                if (validationError != null) {
                    errorMessage = validationError
                } else {
                    errorMessage = null
                    loading = true
                    register(registerData.email, registerData.password) { success, error ->
                        loading = false
                        if (success) {
                            onRegisterSuccess()
                        } else {
                            errorMessage = error ?: "Ocurrió un error"
                        }
                    }
                }
            },
            enabled = !loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (loading) {
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
}

@Preview
@Composable
private fun RegisterScreenPreview() {
    RickAndMortyTheme {
        RegisterScreen(
            onRegisterSuccess = {}, onNavigateToLogin = {},
            register = { _, _, _ -> }
        )
    }
}

data class RegisterData(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = ""
) {
    // Valida que los campos estén completos y las contraseñas coincidan
    fun isValid(): Boolean {
        return email.isNotBlank()
                && password.isNotBlank()
                && confirmPassword.isNotBlank()
                && password == confirmPassword
    }

    // Retorna mensaje de error si hay algún problema, o null si es válido
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

