package com.android.rickandmorty.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun WelcomeScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier){}
}

@Preview
@Composable
private fun WelcomeScreenPrev() {
    WelcomeScreen()
}