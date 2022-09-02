package ir.hossein.digikala_kotlin.checkout.dataSource

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.LoginMessage
import ir.hossein.digikala_kotlin.retrofit.ApiService

class RemoteCheckout(val apiService: ApiService): CheckoutDataSource {
    override fun checkout(orderId: String): Single<LoginMessage> =apiService.checkout(orderId)
}