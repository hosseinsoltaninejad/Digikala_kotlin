package ir.hossein.digikala_kotlin.checkout.repo

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.LoginMessage
import ir.hossein.digikala_kotlin.checkout.dataSource.CheckoutDataSource

class CheckoutRepositoryImpl(private val remoteCheckout: CheckoutDataSource) : CheckoutRepository {
    override fun checkout(orderId: String): Single<LoginMessage> = remoteCheckout.checkout(orderId)
}