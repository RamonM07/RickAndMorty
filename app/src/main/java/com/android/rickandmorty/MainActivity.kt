package com.android.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.android.rickandmorty.ui.navigation.AppNavGraph
import com.android.rickandmorty.ui.theme.RickAndMortyTheme
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {
                Scaffold(topBar = {
                    Text(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.app_name),
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(vertical = 16.dp),
                        style = MaterialTheme.typography.headlineLarge
                    )
                }, modifier = Modifier.fillMaxSize()) { paddingValues ->
                    val isAuthenticated = true
                    val navController = rememberNavController()
                    AppNavGraph(
                        modifier = Modifier.padding(paddingValues),
                        navController = navController,
                        isAuthenticated = isAuthenticated
                    )
                }
            }
        }
    }
}