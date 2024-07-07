package com.libreforge.calendar.domain.model

data class Configuration(
    val server: String,
    val address: String,
    val login: String,
    val password: String,
    var pin: String? = null,
    var rePin: String? = null
)
