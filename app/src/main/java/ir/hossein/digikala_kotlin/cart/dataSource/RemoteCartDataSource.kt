package ir.hossein.digikala_kotlin.cart.dataSource

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.CartItemCount
import ir.hossein.digikala_kotlin.data.CartResponse
import ir.hossein.digikala_kotlin.data.LoginMessage
import ir.hossein.digikala_kotlin.retrofit.ApiService

class RemoteCartDataSource(val apiService: ApiService): CartDataSource {
    override fun addToBasket(productId: String): Single<LoginMessage> = apiService.addToBasket(productId)

    override fun getBasketList(): Single<CartResponse> = apiService.getBasketList()
    override fun changeCartItemCount(productId: String, count: Int): Single<LoginMessage> = apiService.changeCartItemCount(productId, count)

    override fun removeCartItem(id: String): Single<LoginMessage> = apiService.removeCartItem(id)
    override fun getCartItemCount(): Single<CartItemCount> = apiService.getCartItemCount()
}