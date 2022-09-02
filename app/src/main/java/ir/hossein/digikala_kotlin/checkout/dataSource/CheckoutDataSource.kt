package ir.hossein.digikala_kotlin.checkout.dataSource

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.LoginMessage

interface CheckoutDataSource {
    fun checkout(orderId:String):Single<LoginMessage>
}