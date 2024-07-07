package com.libreforge.calendar.domain.usecase

import com.libreforge.calendar.domain.model.Configuration
import com.libreforge.calendar.domain.model.ProgressState
import com.libreforge.calendar.domain.repository.CalendarRepository
import com.libreforge.calendar.domain.utils.ResultState
import com.libreforge.calendar.presentation.utils.Event
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TestConnectionUseCaseImpl @Inject constructor(
    private val calendarRepository: CalendarRepository
) : TestConnectionUseCase {
    override suspend fun invoke(configuration: Configuration): Flow<ResultState<Event<ProgressState>>> {
        return calendarRepository.testConnection(configuration)
    }
}