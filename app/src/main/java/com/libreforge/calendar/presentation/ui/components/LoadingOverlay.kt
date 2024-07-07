package com.libreforge.calendar.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.libreforge.calendar.presentation.ui.theme.ApplicationTheme

@Composable
fun LoadingOverlay(navController: NavController? = null) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White.copy(alpha = 0.5f))
    ) {
        CircularProgressIndicator(color = Color.Black)
        Text(text = "Loading", textAlign = TextAlign.Center)
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingPreview() {
    ApplicationTheme {
        LoadingOverlay()
    }
}