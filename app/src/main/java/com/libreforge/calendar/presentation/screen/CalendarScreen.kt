package com.libreforge.calendar.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.libreforge.calendar.R
import com.libreforge.calendar.presentation.model.CalendarUIEvent
import com.libreforge.calendar.presentation.navigation.HomeScreens
import com.libreforge.calendar.presentation.ui.components.Calendar
import com.libreforge.calendar.presentation.ui.components.InactivityHandler
import com.libreforge.calendar.presentation.ui.theme.ApplicationTheme

@Composable
fun CalendarScreen(navController: NavHostController? = null) {
    val events = listOf(
        CalendarUIEvent("Passed", 2.5f, 3.5f),
        CalendarUIEvent("Meeting #2", 5.25f, 6.75f),
//        CalendarUIEvent("Bottle of beer with Dad", 7f, 8f),
        CalendarUIEvent("Scheduled", 7.75f, 8.5f)
    )

    InactivityHandler(navController = navController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.blue_new))
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                IconButton(
                    onClick = { navController?.navigate(HomeScreens.Contacts.route) },
                    modifier = Modifier.size(48.dp) // Adjust size as needed
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.contacts),
                        contentDescription = "Contacts"
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = { navController?.navigate(HomeScreens.Configuration.route) },
                    modifier = Modifier.size(48.dp) // Adjust size as needed
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.locker),
                        contentDescription = "Unlock screen"
                    )
                }
            }

            Calendar(events)

//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.White)
//            ) {
//                itemsIndexed((0..23).toList()) { _, hour ->
//                    Calendar(hour, sampleEvents.filter { it.startTime <= hour && it.endTime > hour })
//                }
//            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarPreview() {
    ApplicationTheme {
        CalendarScreen()
    }
}