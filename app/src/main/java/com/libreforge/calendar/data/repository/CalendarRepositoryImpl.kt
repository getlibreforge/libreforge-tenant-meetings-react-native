package com.libreforge.calendar.data.repository

import com.libreforge.calendar.data.service.CalendarService
import com.libreforge.calendar.domain.model.Configuration
import com.libreforge.calendar.domain.model.ProgressState
import com.libreforge.calendar.domain.repository.CalendarRepository
import com.libreforge.calendar.domain.utils.ResultState
import com.libreforge.calendar.presentation.utils.Event
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CalendarRepositoryImpl @Inject constructor(
    private val calendarService: CalendarService
) : CalendarRepository {
    override suspend fun saveConfiguration(configuration: Configuration): Flow<ResultState<Event<ProgressState>>> {

        return flow {
            emit(ResultState.Loading())
            delay(3000)
            emit(ResultState.Success(Event(ProgressState.CONFIGURATION_SAVED)))
        }
    }

    override suspend fun testConnection(configuration: Configuration): Flow<ResultState<Event<ProgressState>>> {
        return flow {
            emit(ResultState.Loading())
            delay(3000)
            emit(ResultState.Success(Event(ProgressState.SUCCESS_CONNECTION)))
        }
    }
}