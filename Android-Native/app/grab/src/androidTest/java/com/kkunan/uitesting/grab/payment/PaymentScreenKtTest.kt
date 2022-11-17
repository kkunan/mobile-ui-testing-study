package com.kkunan.uitesting.grab

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.kkunan.uitesting.grab.payment.PaymentScreenViewModel
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class PaymentScreenKtTest {

    private lateinit var viewModel: PaymentScreenViewModel


    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        viewModel = mockk()
    }

    // Payment Text
    @Test
    fun paymentScreen_init_should_display_payment_text_title(){
        // arrange

        // act
        composeTestRule.setContent {
            PaymentScreen(viewModel)
        }

        // assert
        composeTestRule.onNodeWithText("Payment").assertIsDisplayed()
    }
    // Setting button
    // Payment channels horizontal listview

    // Quick Action Text
    // Quick Action horizontal listview

    // Do more with your money text
    // Other service horizontal listview
}