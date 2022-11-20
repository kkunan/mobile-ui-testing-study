package com.kkunan.uitesting.grab

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.kkunan.uitesting.grab.payment.PaymentScreenViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PaymentScreen(viewModel: PaymentScreenViewModel = viewModel()) {
    // Payment Text
    Row {
        Text(text = "Payment")
        // Setting button
        IconButton(onClick = { /*TODO*/ }, Modifier.testTag("settingButton")) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_settings_24),
                contentDescription = "Setting Button"
            )
        }
    }

    // Payment channels horizontal listview
    val channels = viewModel.channels.collectAsState(initial = emptyList())
    LazyRow(Modifier.testTag("paymentChannels")) {
        items(channels.value) {
            Card(modifier = Modifier.clickable {  }) {
                Text(channels.value.first().channelName)
                Text(channels.value.first().channelBalanceStr)
                Text(stringResource(R.string.sgd_currency))
            }
        }
    }


    // Quick Action Text
    Text("Quick Actions")
    // Quick Action horizontal listview
    val actions = viewModel.quickActions.collectAsState(initial = emptyList())
    LazyRow(Modifier.testTag("quickActions")) {
        items(actions.value) { action ->
            IconButton(onClick = { /*TODO*/ }) {
                Text(action.name)
            }
        }
    }

    // Do more with your money text
    Text("Do more with your money")
    // Other service horizontal listview
    val services = viewModel.availableServices.collectAsState(initial = emptyList())
    LazyRow(Modifier.testTag("availableServices")) {
        items(services.value) { service ->
            Card(modifier = Modifier.clickable {  }) {
                Text(service.description)
            }
        }
    }

    // Bottom navigation bar with five tabs *** test where it belongs
}

