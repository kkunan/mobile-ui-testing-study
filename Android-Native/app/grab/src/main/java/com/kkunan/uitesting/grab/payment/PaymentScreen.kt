package com.kkunan.uitesting.grab

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.kkunan.uitesting.grab.payment.PaymentScreenViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PaymentScreen(viewModel: PaymentScreenViewModel = viewModel()){
    // Payment Text
    Text(text = "Payment")
    // Setting button
    // Payment channels horizontal listview

    // Quick Action Text
    // Quick Action horizontal listview

    // Do more with your money text
    // Other service horizontal listview

    // Bottom navigation bar with five tabs *** test where it belongs
}

