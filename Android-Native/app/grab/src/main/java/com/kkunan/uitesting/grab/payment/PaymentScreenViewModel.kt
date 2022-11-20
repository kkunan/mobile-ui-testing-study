package com.kkunan.uitesting.grab.payment

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * This is for the sake of example, won't implement the real one
 */
class PaymentScreenViewModel(
    initChannels: List<PaymentChannelUiState> = emptyList(),
    initActions: List<QuickAction> = emptyList(),
    initServices: List<AvailableService> = emptyList(),
) : ViewModel() {
    val channels: Flow<List<PaymentChannelUiState>> = MutableStateFlow(initChannels)
    val quickActions: Flow<List<QuickAction>> = MutableStateFlow(initActions)
    val availableServices: Flow<List<AvailableService>> = MutableStateFlow(initServices)
}

data class PaymentChannelUiState(
    val channelName: String,
    val channelBalanceStr: String
)

data class QuickAction(val iconResId: Int, val name: String)

data class AvailableService(val imageUrl: String, val description: String)