package com.kkunan.uitesting.grab.payment

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.kkunan.uitesting.grab.PaymentScreen
import com.kkunan.uitesting.grab.R
import com.kkunan.uitesting.grab.utils.getRandomString
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

internal class PaymentScreenActionsTest {

    private lateinit var router: PaymentRouter
    private lateinit var viewModel: PaymentScreenViewModel

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        router = mockk(relaxed = true)
        viewModel = mockk(relaxed = true)

    }

    @Test
    fun setting_clicked_should_call_router_to_setting() {
        // arrange
        composeTestRule.setContent {
            PaymentScreen(router = router, viewModel = viewModel)
        }

        // act
        val setting = composeTestRule.onNodeWithTag("settingButton")
        setting.performClick()

        // assert

        verify { router.routeToSetting() }
    }

    @Test
    fun payment_item_click_should_call_correct_action() {
        // arrange
        val channels = listOf(
            PaymentChannel(
                channelName = getRandomString(10),
                channelDescription = getRandomString(30),
                channelBalanceStr = (Random.nextInt(50) + Random.nextDouble()).toString()
            ),
            PaymentChannel(
                channelName = getRandomString(10),
                channelDescription = getRandomString(30),
                channelBalanceStr = (Random.nextInt(50) + Random.nextDouble()).toString()
            ),
        )
        viewModel = PaymentScreenViewModel(initChannels = channels)
        composeTestRule.setContent {
            PaymentScreen(viewModel, router)
        }

        channels.forEach { channel ->
            // act
            composeTestRule.onNodeWithText(channel.channelName).performClick()
            // assert
            verify { router.routeToPayment(channel) }
        }
    }

    @Test
    fun quick_action_item_click_should_call_correct_action() {
        // arrange
        val actions = listOf(
            QuickAction(R.drawable.ic_baseline_attach_money_24, name = "Top Up"),
            QuickAction(R.drawable.ic_baseline_qr_code_scanner_24, name = "Scan to Pay")
        )
        viewModel = PaymentScreenViewModel(
            initActions = actions
        )

        composeTestRule.setContent {
            PaymentScreen(viewModel, router)
        }

        actions.forEach { action ->
            // act
            composeTestRule.onNodeWithText(action.name).performClick()
            // assert
            verify { router.routeToQuickAction(action) }
        }
    }

    @Test
    fun service_item_clicked_should_call_correct_action(){
        // arrange
        val services = listOf(
            AvailableService(
                imageUrl = "",
                description = getRandomString(10)
            )
        )
        viewModel = PaymentScreenViewModel(initServices = services)

        composeTestRule.setContent {
            PaymentScreen(viewModel, router)
        }

        // assert
        services.forEach { service ->
            // act
            composeTestRule.onNodeWithText(service.description).performClick()
            // assert
            verify { router.routeToService(service) }
        }
    }
}