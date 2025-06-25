package com.android.rickandmorty.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.rickandmorty.R
import com.android.rickandmorty.data.local.AuthUiState
import com.android.rickandmorty.ui.theme.RickAndMortyTheme
import com.android.rickandmorty.ui.view.viewModel.AuthViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authState by viewModel.authState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.title_login),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp),
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            singleLine = true,
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.title_email)) }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.title_password)) },
            visualTransformation = PasswordVisualTransformation()
        )

        Button(
            modifier = Modifier.padding(top = 16.dp),
            onClick = {
                viewModel.login(email.trim(), password.trim())
            }
        ) {
            Text(stringResource(R.string.title_login_button))
        }

        TextButton(
            modifier = Modifier.padding(top = 4.dp),
            onClick = onNavigateToRegister
        ) {
            Text(stringResource(R.string.title_register))
        }

        when (authState) {
            is AuthUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(top = 8.dp))
            }

            is AuthUiState.Success -> {
                // Llama onLoginSuccess solo una vez
                LaunchedEffect(Unit) {
                    onLoginSuccess()
                }
            }

            is AuthUiState.Error -> {
                val message = (authState as AuthUiState.Error).message
                Text(
                    text = message ?: "Error desconocido",
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            else -> Unit
        }
    }
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RickAndMortyTheme {
        Greeting("Android")
    }
}