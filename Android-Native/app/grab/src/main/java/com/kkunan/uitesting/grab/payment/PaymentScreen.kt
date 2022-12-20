package com.kkunan.uitesting.grab

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.BoxScope.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kkunan.uitesting.grab.payment.*
import com.kkunan.uitesting.grab.ui.theme.MyApplicationTheme

@Composable
fun PaymentScreen(
    viewModel: PaymentScreenViewModel = viewModel(),
    router: PaymentRouter = PaymentRouterImpl()
) {
    Surface {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                // Payment Text
                Text(text = "Payment")
                // Setting button
                IconButton(
                    onClick = { router.routeToSetting() },
                    Modifier.testTag("settingButton")
                ) {
                    Icon(
                        modifier = Modifier.padding(16.dp),
                        painter = painterResource(id = R.drawable.ic_baseline_settings_24),
                        contentDescription = "Setting Button"
                    )
                }
            }


            // Payment channels horizontal listview
            val channels = viewModel.channels.collectAsState(initial = emptyList())
            PaymentChannelsView(channels.value, onPaymentClicked = { channel ->
                router.routeToPayment(channel)
            })


            // Quick Action
            Slot(title = "Quick Actions", body = {
                val actions = viewModel.quickActions.collectAsState(initial = emptyList())
                LazyRow(Modifier.testTag("quickActions")) {
                    items(actions.value) { action ->
                        IconButton(onClick = { router.routeToQuickAction(action) }) {
                            Text(action.name)
                        }
                    }
                }
            })

            // Other service horizontal listview
            Slot(title = "Do more with your money", body = {
                val services = viewModel.availableServices.collectAsState(initial = emptyList())
                LazyRow(Modifier.testTag("availableServices")) {
                    items(services.value) { service ->
                        Card(modifier = Modifier.clickable { router.routeToService(service) }) {
                            Text(service.description)
                        }
                    }
                }
            })

        }
    }
    // Bottom navigation bar with five tabs *** test where it belongs
}

@Composable
private fun Slot(title: String, body: @Composable () -> Unit, modifier: Modifier = Modifier){
    Column(modifier = modifier) {
        Text(title)
        body()
    }
}

@Composable
private fun PaymentChannelsView(
    channels: List<PaymentChannel>,
    onPaymentClicked: (PaymentChannel) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(modifier.testTag("paymentChannels")) {
        items(channels) { channel ->
            Card(modifier = Modifier
                .width(350.dp)
                .height(200.dp)
                .clickable { onPaymentClicked(channel) }) {
                Box {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(channel.channelName)
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    modifier = Modifier.padding(16.dp),
                                    painter = painterResource(id = R.drawable.ic_baseline_more_horiz_24),
                                    contentDescription = "$channel more actions"
                                )
                            }
                        }

                        Row {
                            Text(stringResource(R.string.sgd_currency))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(channel.channelBalanceStr)
                        }
                    }
                    Row(modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                        .fillMaxWidth()
                        .semantics(mergeDescendants = true, {})
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_credit_card_24),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = channel.channelDescription)
                        Spacer(modifier = Modifier.weight(1.0f))
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_right_24),
                            contentDescription = null,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PaymentChannelsViewPreview() {
    MyApplicationTheme {
        PaymentChannelsView(channels = listOf(
            PaymentChannel(
                "GrabPay Wallet",
                channelDescription = "View GrabPay Card details",
                channelBalanceStr = "20.00"
            )
        ), onPaymentClicked = { _ ->

        })
    }
}

@Preview
@Composable
fun PaymentScreenPreview() {
    MyApplicationTheme {
        PaymentScreen()
    }
}


