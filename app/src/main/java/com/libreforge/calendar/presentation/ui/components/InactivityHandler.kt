package com.libreforge.calendar.presentation.ui.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.navigation.NavController
import com.libreforge.calendar.presentation.navigation.HomeScreens
import kotlinx.coroutines.delay

@Composable
fun InactivityHandler(
    navController: NavController? = null,
    content: @Composable () -> Unit
) {
    var lastInteractionTime by remember { mutableLongStateOf(System.currentTimeMillis()) }

    LaunchedEffect(key1 = Unit) {
        while (true) {
            delay(1000)
            if (System.currentTimeMillis() - lastInteractionTime > 1000_000) {
                navController?.navigate(HomeScreens.ScreenSaver.route)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    lastInteractionTime = System.currentTimeMillis()
                }
            }
    ) {
        content()
    }
}