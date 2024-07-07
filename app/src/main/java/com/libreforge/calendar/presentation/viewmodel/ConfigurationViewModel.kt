package com.libreforge.calendar.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.libreforge.calendar.domain.model.ProgressState
import com.libreforge.calendar.domain.usecase.SaveConfigurationUseCase
import com.libreforge.calendar.domain.usecase.TestConnectionUseCase
import com.libreforge.calendar.domain.utils.ResultState
import com.libreforge.calendar.presentation.model.ConfigurationUIState
import com.libreforge.calendar.presentation.model.toConfiguration
import com.libreforge.calendar.presentation.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ConfigurationViewModel @Inject constructor(
    private val saveConfigurationUseCase: SaveConfigurationUseCase,
    private val testConnectionUseCase: TestConnectionUseCase
) : ViewModel() {

    // Example state variables to save
    var selectedServer by mutableStateOf("")
    var selectedExperience by mutableStateOf("")
    var address by mutableStateOf("")
    var login by mutableStateOf("")
    var password by mutableStateOf("")
    var pin by mutableStateOf("")
    var reEnterPin by mutableStateOf("")

    private val _configuration = MutableStateFlow<ResultState<Event<ProgressState>>>(ResultState.Success(Event(ProgressState.INITIATED)))
    val mConfiguration: StateFlow<ResultState<Event<ProgressState>>> get() = _configuration

    fun saveConfiguration(configurationUIState: ConfigurationUIState) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                saveConfigurationUseCase.invoke(configurationUIState.toConfiguration()).onStart {
                    _configuration.value = ResultState.Loading()
                }.catch { exception ->
                    // Handle error
                }.collect { result ->
                    when (result) {
                        is ResultState.Success -> {
                            _configuration.value = ResultState.Success(Event(result.data.peekContent()))
                        }
                        is ResultState.Error -> {
                            // Handle error
                        }
                        else -> {
                            _configuration.value = result
                        }
                    }
                }
            }
        }
    }

    fun testConnection(configurationUIState: ConfigurationUIState) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                testConnectionUseCase.invoke(configurationUIState.toConfiguration()).onStart {
                    _configuration.value = ResultState.Loading()
                }.catch { exception ->
                    // Handle error
                }.collect { result ->
                    when (result) {
                        is ResultState.Success -> {
                            _configuration.value = ResultState.Success(Event(result.data.peekContent()))
                        }
                        is ResultState.Error -> {
                            // Handle error
                        }
                        else -> {
                            _configuration.value = result
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val ERROR_CODE_UNKNOWN = 0
        const val ERROR_MESSAGE_UNKNOWN = "Unknown error message"
        const val ERROR_CODE_EMPTY_LIST = 1
        const val ERROR_MESSAGE_EMPTY_LIST = "There are no results found for your request"
    }
}