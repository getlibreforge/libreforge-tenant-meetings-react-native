package com.libreforge.calendar.data.service

import com.libreforge.calendar.data.model.ConfigurationRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CalendarService {
    @POST("/configuration")
    fun saveConfiguration(@Body request: ConfigurationRequest): Call<Void>

    @POST("/configuration/test")
    fun testConfiguration(@Body request: ConfigurationRequest): Call<Void>
}