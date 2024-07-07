package com.libreforge.calendar.presentation.screen

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.libreforge.calendar.R
import com.libreforge.calendar.domain.model.ProgressState
import com.libreforge.calendar.domain.utils.ResultState
import com.libreforge.calendar.presentation.model.ConfigurationUIState
import com.libreforge.calendar.presentation.navigation.HomeScreens
import com.libreforge.calendar.presentation.ui.components.DropdownMenu
import com.libreforge.calendar.presentation.ui.components.LoadingOverlay
import com.libreforge.calendar.presentation.ui.theme.ApplicationTheme
import com.libreforge.calendar.presentation.viewmodel.ConfigurationViewModel

@Composable
fun ConfigurationScreen(
    navController: NavController? = null,
    viewModel: ConfigurationViewModel? = hiltViewModel()
) {
    val uiState = viewModel?.mConfiguration?.collectAsState()
    val context = LocalContext.current
    var showLoadingOverlay by remember { mutableStateOf(false) }

    DisposableEffect(navController) {
        val observer = NavController.OnDestinationChangedListener { _, _, _ ->
        }
        navController?.addOnDestinationChangedListener(observer)
        onDispose {
            navController?.removeOnDestinationChangedListener(observer)
        }
    }

    when (val result = uiState?.value) {
        is ResultState.Loading -> {
            showLoadingOverlay = true
        }
        is ResultState.Error -> {
            showLoadingOverlay = false
        }
        is ResultState.Success -> {
            showLoadingOverlay = false
            result.data.getContentIfNotHandled()?.let { progressState ->
                when (progressState) {
                    ProgressState.INITIATED -> {
                        Toast.makeText(context, "INITIATED", Toast.LENGTH_SHORT).show()
                    }
                    ProgressState.CONFIGURATION_SAVED -> {
                        navController?.navigate(HomeScreens.Calendar.route)
                    }
                    ProgressState.CONFIGURATION_NOT_SAVED -> {
                        Toast.makeText(context, "CONFIGURATION NOT SAVED", Toast.LENGTH_SHORT).show()
                    }
                    ProgressState.SUCCESS_CONNECTION -> {
                        Toast.makeText(context, "SUCCESS CONNECTION", Toast.LENGTH_SHORT).show()
                    }
                    ProgressState.FAILED_CONNECTION -> {
                        Toast.makeText(context, "FAILED CONNECTION", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        else -> {
            showLoadingOverlay = false
            // Handle other states if needed
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        ConfigurationScreenState(viewModel, navController)
        if (showLoadingOverlay) {
            LoadingOverlay()
        }
    }
}

@Composable
private fun ConfigurationScreenState(
    viewModel: ConfigurationViewModel?, navController: NavController?
) {
    val context = LocalContext.current
    val activity = context as? Activity

    val serverOptions = listOf(
        stringResource(id = R.string.lbl_server_option1),
        stringResource(id = R.string.lbl_server_option2)
    )
    val experienceOptions = listOf(
        stringResource(id = R.string.lbl_experience_option1),
        stringResource(id = R.string.lbl_experience_option2),
        stringResource(id = R.string.lbl_experience_option3)
    )

    val selectedServer = viewModel?.selectedServer ?: ""
    val selectedExperience = viewModel?.selectedExperience ?: ""
    val address = viewModel?.address ?: ""
    val login = viewModel?.login ?: ""
    val password = viewModel?.password ?: ""
    val pin = viewModel?.pin ?: ""
    val reEnterPin = viewModel?.reEnterPin ?: ""

    // Update ViewModel when fields change
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.txt_configuration_screen),
            fontSize = 24.sp,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        // Server and Address
        Column(modifier = Modifier.fillMaxWidth()) {
            DropdownMenu(options = serverOptions,
                label = stringResource(id = R.string.lbl_server),
                selectedOption = selectedServer,
                onOptionSelected = { viewModel?.selectedServer = it })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = address,
                onValueChange = { viewModel?.address = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(id = R.string.lbl_address)) })
        }

        // Choose Experience
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.txt_choose_experience),
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.height(8.dp))
            DropdownMenu(options = experienceOptions,
                label = stringResource(id = R.string.lbl_experience),
                selectedOption = selectedExperience,
                onOptionSelected = { viewModel?.selectedExperience = it })
        }

        // Credentials
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.txt_credentials),
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = login,
                onValueChange = { viewModel?.login = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(id = R.string.lbl_login)) })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = password,
                onValueChange = { viewModel?.password = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(id = R.string.lbl_password)) },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    viewModel?.testConnection(
                        ConfigurationUIState(
                            selectedServer, address, login, password
                        )
                    )
                }, modifier = Modifier.align(Alignment.End)
            ) {
                Text(stringResource(id = R.string.btn_test_connection))
            }
        }

        // PIN
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.txt_pin),
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.End
            )
            Text(
                text = stringResource(id = R.string.txt_pin_description),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = pin,
                onValueChange = { viewModel?.pin = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(id = R.string.lbl_enter)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = reEnterPin,
                onValueChange = { viewModel?.reEnterPin = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(id = R.string.lbl_re_enter)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = PasswordVisualTransformation()
            )
        }

        // Buttons
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Absolute.Right
        ) {
            Button(
                onClick = {
                    activity?.finish()
                }, modifier = Modifier.padding(10.dp)
            ) {
                Text(stringResource(id = R.string.btn_exit))
            }
            Button(
                onClick = {
                    viewModel?.saveConfiguration(
                        ConfigurationUIState(
                            selectedServer, address, login, password
                        )
                    )
                }, modifier = Modifier.padding(10.dp)
            ) {
                Text(stringResource(id = R.string.btn_save))
            }
        }
    }
}

@Preview(showBackground = true, name = "10-inch Tablet Portrait", widthDp = 600, heightDp = 960)
@Composable
fun ConfigurationPreview() {
    ApplicationTheme {
        ConfigurationScreenState(null, null)
    }
}