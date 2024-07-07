package com.libreforge.calendar.di

import com.libreforge.calendar.domain.repository.CalendarRepository
import com.libreforge.calendar.domain.usecase.SaveConfigurationUseCase
import com.libreforge.calendar.domain.usecase.SaveConfigurationUseCaseImpl
import com.libreforge.calendar.domain.usecase.TestConnectionUseCase
import com.libreforge.calendar.domain.usecase.TestConnectionUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Inject

@Module
@InstallIn(ViewModelComponent::class)
class CommonUseCaseModule {
    @Provides
    @Inject
    fun provideSaveConfigurationUseCase(calendarRepository: CalendarRepository): SaveConfigurationUseCase =
        SaveConfigurationUseCaseImpl(calendarRepository)

    @Provides
    @Inject
    fun provideTestConnectionUseCase(calendarRepository: CalendarRepository): TestConnectionUseCase =
        TestConnectionUseCaseImpl(calendarRepository)
}