package ir.hossein.digikala_kotlin.cart.repo

import io.reactivex.Single
import ir.hossein.digikala_kotlin.cart.dataSource.CartDataSource
import ir.hossein.digikala_kotlin.data.CartItemCount
import ir.hossein.digikala_kotlin.data.CartResponse
import ir.hossein.digikala_kotlin.data.LoginMessage

class CartRepositoryImpl(private val remoteCartDataSource: CartDataSource): CartRepository {
    override fun addToBasket(productId: String): Single<LoginMessage> = remoteCartDataSource.addToBasket(productId)

    override fun getBasketList(): Single<CartResponse> = remoteCartDataSource.getBasketList()

    override fun changeCartItemCount(productId: String, count: Int): Single<LoginMessage> = remoteCartDataSource.changeCartItemCount(productId, count)
    override fun removeCartItem(id: String): Single<LoginMessage> = remoteCartDataSource.removeCartItem(id)
    override fun getCartItemCount(): Single<CartItemCount> = remoteCartDataSource.getCartItemCount()
}