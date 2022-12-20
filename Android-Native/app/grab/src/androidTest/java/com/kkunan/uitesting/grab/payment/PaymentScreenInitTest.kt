package com.kkunan.uitesting.grab.payment

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.kkunan.uitesting.grab.PaymentScreen
import com.kkunan.uitesting.grab.R
import com.kkunan.uitesting.grab.utils.getRandomString
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

internal class PaymentScreenInitTest {

    private lateinit var viewModel: PaymentScreenViewModel

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        viewModel = mockk(relaxed = true)
    }

    //region Payment Text
    @Test
    fun init_should_display_payment_text_title() {
        // arrange
        composeTestRule.setContent {
            PaymentScreen(viewModel)
        }

        // assert
        composeTestRule.onNodeWithText("Payment").assertIsDisplayed()
    }

    // endregion

    // region Setting button
    @Test
    fun init_should_display_setting_button_with_correct_setup() {
        // arrange
        composeTestRule.setContent {
            PaymentScreen(viewModel)
        }

        // assert
        val setting = composeTestRule.onNodeWithTag("settingButton")
        setting.assertIsDisplayed()
        setting.assertHasClickAction()
    }

    // endregion

    // region Payment channels horizontal listview
    @Test
    fun init_should_display_payment_channels_with_the_channel_info_text() {
        // arrange
        val channel = PaymentChannel(
            channelName = getRandomString(10),
            channelDescription = getRandomString(30),
            channelBalanceStr = (Random.nextInt(50) + Random.nextDouble()).toString()
        )
        viewModel = PaymentScreenViewModel(initChannels = listOf(channel))
        composeTestRule.setContent {
            PaymentScreen(viewModel)
        }

        // assert
        val horizontalListView = composeTestRule.onNodeWithTag("paymentChannels")
        horizontalListView.assertIsDisplayed()
        val context = InstrumentationRegistry.getInstrumentation().context
        horizontalListView.assert(
            hasAnyDescendant(hasText(channel.channelName))
                    and hasAnyDescendant(
                hasText(channel.channelBalanceStr)
            )
        )
        // for some reasons if put another and up there it fails
        horizontalListView.assert(
            hasAnyDescendant(hasText(context.getString(R.string.sgd_currency)))
        )
        horizontalListView.assert(
            hasAnyDescendant(hasText(channel.channelDescription))
        )
        horizontalListView.assert(hasScrollAction())
    }
    // endregion

    // region Quick Action
    @Test
    fun init_should_see_quick_actions_text() {
        // arrange
        composeTestRule.setContent {
            PaymentScreen(viewModel)
        }

        // assert
        composeTestRule.onNodeWithText("Quick Actions").assertIsDisplayed()
    }


    // Quick Action horizontal listview
    @Test
    fun init_should_display_quick_action_items() {
        // arrange
        val actions = listOf(
            QuickAction(R.drawable.ic_baseline_attach_money_24, name = "Top Up"),
            QuickAction(R.drawable.ic_baseline_qr_code_scanner_24, name = "Scan to Pay")
        )
        viewModel = PaymentScreenViewModel(
            initActions = actions
        )
        composeTestRule.setContent {
            PaymentScreen(viewModel)
        }

        // assert
        val quickActions = composeTestRule.onNodeWithTag("quickActions")
        quickActions.printToLog("quickActions")

        quickActions.assertIsDisplayed()
        quickActions.assert(hasScrollAction())
        actions.forEach { action ->
            quickActions.assert(
                hasAnyDescendant(
                    hasText(action.name)
                            and hasClickAction()
                )
            )
        }
    }
    // endregion

    // region Other service
    @Test
    fun init_should_display_do_more_with_your_money_text() {
        // arrange
        composeTestRule.setContent {
            PaymentScreen(viewModel)
        }

        // assert
        composeTestRule.onNodeWithText("Do more with your money").assertIsDisplayed()
    }

    // Other service horizontal listview
    @Test
    fun init_should_display_services_description() {
        // arrange
        val services = listOf(
            AvailableService(
                imageUrl = "",
                description = getRandomString(10)
            )
        )
        viewModel = PaymentScreenViewModel(initServices = services)

        composeTestRule.setContent {
            PaymentScreen(viewModel)
        }

        // assert
        val availableServices = composeTestRule.onNodeWithTag("availableServices")
        availableServices.assertIsDisplayed()
        services.forEach { service ->
            availableServices.assert(hasAnyDescendant(hasText(service.description) and hasClickAction()))
        }
    }
    // endregion
}