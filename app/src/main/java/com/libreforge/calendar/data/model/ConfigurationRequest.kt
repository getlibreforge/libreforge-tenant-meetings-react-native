package com.libreforge.calendar.data.model

import com.libreforge.calendar.domain.model.Configuration

data class ConfigurationRequest(
    val server: String,
    val address: String,
    val login: String,
    val password: String,
    var pin: String? = null,
    var rePin: String? = null
)

fun Configuration.toConfigurationRequest(): ConfigurationRequest {
    val configurationRequest = ConfigurationRequest(server, address, login, password)
    this.pin?.let {
        configurationRequest.pin = this.pin
    }
    this.rePin?.let {
        configurationRequest.rePin = this.rePin
    }
    return configurationRequest
}
