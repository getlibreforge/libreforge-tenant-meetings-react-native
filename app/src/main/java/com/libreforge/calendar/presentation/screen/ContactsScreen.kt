package com.libreforge.calendar.presentation.screen

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.libreforge.calendar.R
import com.libreforge.calendar.presentation.ui.theme.ApplicationTheme

@Composable
fun ContactsScreen(navController: NavHostController? = null) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Contacts",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Elevator Issues, Dial: 833-231-2519")
            Text(text = "Emergency, Dial: 911")
        }

        Image(
            painter = painterResource(id = R.drawable.ambulance), // replace with your drawable resource
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = { (context as? Activity)?.finish() },
                modifier = Modifier.padding(10.dp)
            ) {
                Text(stringResource(id = R.string.btn_exit))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactsPreview() {
    ApplicationTheme {
        ContactsScreen()
    }
}