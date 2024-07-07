package com.libreforge.calendar.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.libreforge.calendar.R
import com.libreforge.calendar.presentation.model.CalendarUIEvent
import com.libreforge.calendar.presentation.ui.theme.ApplicationTheme

@Composable
fun Calendar(events: List<CalendarUIEvent>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .background(colorResource(id = R.color.blue_new))
    ) {
        val hourHeight = 80.dp
        val hours = (0..23).toList()

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(hours) { index, hour ->
                // Draw each hour item with its separator
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(hourHeight)
                ) {
                    if (!isIndexInAnyEventRange(events, index)) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Divider line extending to the end of the screen
                            Divider(
                                color = Color.Black, thickness = 1.dp, modifier = Modifier.weight(1f)
                            )
                            // Hour text
                            Text(
                                text = String.format("%02d:00", hour),
                                modifier = Modifier.padding(start = 8.dp),
                                fontSize = 16.sp
                            )
                            Image(
                                painter = painterResource(id = R.drawable.clock), // Replace with your clock icon resource
                                contentDescription = "Clock icon",
                                modifier = Modifier
                                    .size(26.dp)
                                    .padding(start = 4.dp)
                            )
                        }
                    }
                }
            }
        }


        // Draw the events
        events.forEach { event ->
            val startHour = event.startTime.toInt()
            val startOffset = (event.startTime - startHour) * hourHeight.value
            val eventHeight = (event.endTime - event.startTime) * hourHeight.value

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (hourHeight.value / 2 + startHour * hourHeight.value + startOffset).dp)
                    .height(eventHeight.dp)
                    .background(Color.White) // Light red background
                    .border(2.dp, Color.Black)
                    .padding(15.dp),
                contentAlignment = Alignment.TopStart // Align content to start (left)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween // Align children horizontally with space between them
                ) {
                    // Event name on the left
                    Text(
                        text = event.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(end = 8.dp)
                    )

                    // Right side: Event start time, end time, and clock icon
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${event.startTime} - ${event.endTime}",
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                        Image(
                            painter = painterResource(id = R.drawable.clock), // Replace with your clock icon resource
                            contentDescription = "Clock icon",
                            modifier = Modifier
                                .size(26.dp)
                                .padding(start = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

fun isIndexInAnyEventRange(events: List<CalendarUIEvent>, index: Int): Boolean {
    for (event in events) {
        if (index >= event.startTime && index <= event.endTime) {
            return true
        }
    }
    return false
}

@Preview(showBackground = true)
@Composable
fun CalendarPreview() {
    val events = listOf(
        CalendarUIEvent("Passed", 2.5f, 3.5f),
        CalendarUIEvent("Meeting #2", 5.25f, 6.75f),
//        CalendarUIEvent("Bottle of beer with Dad", 7f, 8f),
        CalendarUIEvent("Scheduled", 7.75f, 8.5f)
    )

    ApplicationTheme {
        Calendar(events)
    }
}