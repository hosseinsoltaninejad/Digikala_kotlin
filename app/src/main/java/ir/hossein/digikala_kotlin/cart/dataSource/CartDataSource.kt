package ir.hossein.digikala_kotlin.cart.dataSource

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.CartItemCount
import ir.hossein.digikala_kotlin.data.CartResponse
import ir.hossein.digikala_kotlin.data.LoginMessage

interface CartDataSource {
    fun addToBasket(productId:String):Single<LoginMessage>
    fun getBasketList():Single<CartResponse>
    fun changeCartItemCount(productId:String,count:Int):Single<LoginMessage>
    fun removeCartItem(id:String):Single<LoginMessage>
    fun getCartItemCount():Single<CartItemCount>
}