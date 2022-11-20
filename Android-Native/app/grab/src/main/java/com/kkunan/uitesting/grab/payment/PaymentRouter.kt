package com.kkunan.uitesting.grab.payment


interface PaymentRouter {
    fun routeToSetting()
    fun routeToPayment(paymentChannel: PaymentChannel)
    fun routeToQuickAction(quickAction: QuickAction)
    fun routeToService(service: AvailableService)
}

/**
 * Won't implement this as it's out of scope
 */
class PaymentRouterImpl : PaymentRouter {
    override fun routeToSetting() {

    }

    override fun routeToPayment(paymentChannel: PaymentChannel) {
    }

    override fun routeToQuickAction(quickAction: QuickAction) {

    }

    override fun routeToService(service: AvailableService) {

    }

}