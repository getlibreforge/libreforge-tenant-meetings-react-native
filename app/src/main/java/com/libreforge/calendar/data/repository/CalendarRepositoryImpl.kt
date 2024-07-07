package com.libreforge.calendar.data.repository

import com.libreforge.calendar.data.model.toConfigurationRequest
import com.libreforge.calendar.data.service.CalendarService
import com.libreforge.calendar.data.utils.buildRespResult
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
//            emit(ResultState.Success(ProgressState.CONFIGURATION_NOT_SAVED))
        }
    }

    //        val saveConfigurationResponse =
//            calendarService.saveConfiguration(configuration.toConfigurationRequest()).execute()
//
//        return flow {
//            buildRespResult(saveConfigurationResponse).fold({
//                if (it != null) {
//                } else {
//                    handleError()
//                }
//            }, {
//                handleError(it)
//            })
//        }


    override suspend fun testConnection(configuration: Configuration): Flow<ResultState<Event<ProgressState>>> {
        return flow {
            emit(ResultState.Loading())
            delay(3000)
            emit(ResultState.Success(Event(ProgressState.SUCCESS_CONNECTION)))
//            emit(ResultState.Success(ProgressState.FAILED_CONNECTION))
        }
    }

    //    override suspend fun getAllCountries(): Flow<ResultState<List<Country>>> {
//        val getCountriesResponse = countryService.getAllCountries().execute()
//        return flow {
//            buildRespResult(getCountriesResponse).fold({
//                if (it != null) {
//                    if (it.isEmpty()) {
//                        handleError(ERROR_CODE_EMPTY_LIST, ERROR_MESSAGE_EMPTY_LIST)
//                    } else {
//                        val countries = mutableListOf<Country>()
//                        it.forEach { country ->
//                            countries.add(country.toCountry())
//                        }
//                        emit(ResultState.Success(countries))
//                    }
//                } else {
//                    handleError()
//                }
//            }, {
//                handleError(it)
//            })
//        }
//    }
//
//    override suspend fun getCountry(name: String): Flow<ResultState<Country>> {
//        val getCountryResponse = countryService.getCountry(name).execute()
//        return flow {
//            buildRespResult(getCountryResponse).fold({
//                if (it != null) {
//                    if (it.isEmpty()) {
//                        handleError(ERROR_CODE_EMPTY_LIST, ERROR_MESSAGE_EMPTY_LIST)
//                    } else {
//                        val country = it[0].toCountry()
//                        emit(ResultState.Success(country))
//                    }
//                } else {
//                    handleError()
//                }
//            }, {
//                handleError(it)
//            })
//        }
//    }
//
    private fun handleError(error: Throwable? = null): Flow<ResultState<Any>> {
        return flow {
            if (error != null) {
                val errorCode = error.cause?.message?.toInt() ?: ERROR_CODE_UNKNOWN
                emit(ResultState.Error(errorCode, error.message.toString()))
            } else {
                emit(ResultState.Error(ERROR_CODE_UNKNOWN, ERROR_MESSAGE_UNKNOWN))
            }
        }
    }

    private fun handleError(errorCode: Int, errorMessage: String): Flow<ResultState<Any>> {
        return flow {
            emit(ResultState.Error(errorCode, errorMessage))
        }
    }

    companion object {
        const val ERROR_CODE_UNKNOWN = 0
        const val ERROR_MESSAGE_UNKNOWN = "Unknown error message"
        const val ERROR_CODE_EMPTY_LIST = 1
        const val ERROR_MESSAGE_EMPTY_LIST = "There are no results found for your request"
    }
}