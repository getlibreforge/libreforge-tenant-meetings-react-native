package com.libreforge.calendar.presentation.navigation

private const val ROUTE_CONFIGURATION = "Configuration"
private const val ROUTE_LOADING = "Loading"
private const val ROUTE_ERROR = "Error"
private const val ROUTE_CALENDAR = "Calendar"
private const val ROUTE_NEWS = "News"
private const val ROUTE_CONTACTS = "Contacts"
private const val ROUTE_SCREEN_SAVER = "ScreenSaver"

sealed class Screen(val route: String) {
    fun <T> addArgs(vararg args: T): String {
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }
}

sealed class HomeScreens(route: String) : Screen(route) {
    object Configuration : HomeScreens(ROUTE_CONFIGURATION)
    object Loading : HomeScreens(ROUTE_LOADING)
    object Error : HomeScreens(ROUTE_ERROR)
    object Calendar : HomeScreens(ROUTE_CALENDAR)
    object News : HomeScreens(ROUTE_NEWS)
    object Contacts : HomeScreens(ROUTE_CONTACTS)
    object ScreenSaver : HomeScreens(ROUTE_SCREEN_SAVER)
}