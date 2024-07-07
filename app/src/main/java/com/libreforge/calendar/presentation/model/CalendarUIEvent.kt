package com.libreforge.calendar.presentation.model

data class CalendarUIEvent(
    val name: String,
    val startTime: Float, // e.g., 13.5 for 1:30 PM
    val endTime: Float // e.g., 14.5 for 2:30 PM
)