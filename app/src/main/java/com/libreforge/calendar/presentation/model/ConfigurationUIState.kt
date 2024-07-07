package com.libreforge.calendar.presentation.model

import com.libreforge.calendar.domain.model.Configuration

data class ConfigurationUIState(
    val server: String,
    val address: String,
    val login: String,
    val password: String,
    var pin: String? = null,
    var rePin: String? = null
)

fun ConfigurationUIState.toConfiguration(): Configuration {
    val configuration = Configuration(server, address, login, password)
    this.pin?.let {
        configuration.pin = this.pin
    }
    this.rePin?.let {
        configuration.rePin = this.rePin
    }
    return configuration
}
