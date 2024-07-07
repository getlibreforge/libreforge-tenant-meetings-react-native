package com.libreforge.calendar.presentation.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterTransition.Companion.None
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.libreforge.calendar.presentation.screen.CalendarScreen
import com.libreforge.calendar.presentation.screen.ConfigurationScreen
import com.libreforge.calendar.presentation.screen.ContactsScreen
import com.libreforge.calendar.presentation.screen.ErrorScreen
import com.libreforge.calendar.presentation.ui.components.LoadingOverlay
import com.libreforge.calendar.presentation.screen.NewsScreen
import com.libreforge.calendar.presentation.screen.ScreenSaverScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalUnitApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun MainNavHost(navController: NavHostController, viewModelStoreOwner: ViewModelStoreOwner) {
    NavHost(
        navController = navController,
        startDestination = HomeScreens.Configuration.route
//        enterTransition = { None },
//        exitTransition = { ExitTransition.None },
//        popEnterTransition = { None },
//        popExitTransition = { ExitTransition.None },
    ) {
        composable(HomeScreens.Configuration.route,
            enterTransition = { fadeIn(animationSpec = tween(500)) },
            exitTransition = { fadeOut(animationSpec = tween(500)) },
            popEnterTransition = { fadeIn(animationSpec = tween(500)) },
            popExitTransition = { fadeOut(animationSpec = tween(500)) }) {
            BackHandler(enabled = true) {}
            ConfigurationScreen(navController)
        }
        composable(HomeScreens.Loading.route) {
            BackHandler(enabled = true) {}
            LoadingOverlay(navController)
        }
        composable(HomeScreens.Error.route) {
            ErrorScreen()
        }
        composable(HomeScreens.Calendar.route,
            enterTransition = { fadeIn(animationSpec = tween(500)) },
            exitTransition = { fadeOut(animationSpec = tween(500)) },
            popEnterTransition = { fadeIn(animationSpec = tween(500)) },
            popExitTransition = { fadeOut(animationSpec = tween(500)) }) {
            BackHandler(enabled = true) {}
            CalendarScreen(navController)
        }
        composable(HomeScreens.News.route) {
            NewsScreen()
        }
        composable(HomeScreens.Contacts.route) {
            ContactsScreen(navController)
        }
        composable(HomeScreens.ScreenSaver.route) {
            ScreenSaverScreen()
        }
    }
}