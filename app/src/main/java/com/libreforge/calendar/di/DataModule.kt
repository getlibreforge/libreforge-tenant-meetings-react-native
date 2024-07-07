package com.libreforge.calendar.di

import com.libreforge.calendar.data.repository.CalendarRepositoryImpl
import com.libreforge.calendar.data.service.CalendarService
import com.libreforge.calendar.domain.repository.CalendarRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import javax.inject.Inject

@Module
@InstallIn(ActivityRetainedComponent::class)
class DataModule {
    @Provides
    @Inject
    fun provideEmsRepository(calendarService: CalendarService): CalendarRepository =
        CalendarRepositoryImpl(calendarService)
}