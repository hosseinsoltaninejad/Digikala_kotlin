package ir.hossein.digikala_kotlin.data

import ir.hossein.digikala_kotlin.data.CartItem

data class CartResponse(
    var message: List<CartItem>,
    val status: String,
    var totalPrice:Int
)