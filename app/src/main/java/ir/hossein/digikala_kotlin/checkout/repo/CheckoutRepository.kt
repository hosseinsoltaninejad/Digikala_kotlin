package ir.hossein.digikala_kotlin.checkout.repo

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.LoginMessage

interface CheckoutRepository {
    fun checkout(orderId:String):Single<LoginMessage>
}