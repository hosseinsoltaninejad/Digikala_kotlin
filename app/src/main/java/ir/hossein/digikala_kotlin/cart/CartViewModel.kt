package ir.hossein.digikala_kotlin.cart

import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
import ir.hossein.digikala_kotlin.R
import ir.hossein.digikala_kotlin.cart.repo.CartRepository
import ir.hossein.digikala_kotlin.data.CartItem
import ir.hossein.digikala_kotlin.data.CartItemCount
import ir.hossein.digikala_kotlin.data.CartResponse
import ir.hossein.digikala_kotlin.data.EmptyState
import ir.hossein.digikala_kotlin.login.TokenHolder
import ir.hossein.digikala_kotlin.utils.BaseViewModel
import ir.hossein.digikala_kotlin.utils.MySingleObserver
import ir.hossein.digikala_kotlin.utils.singleHelper
import org.greenrobot.eventbus.EventBus

class CartViewModel(private val cartRepository: CartRepository) : BaseViewModel() {

    val cartResponseLiveData = MutableLiveData<CartResponse>()
    val totalPriceLiveData = MutableLiveData<Int>()
    val emptyStateLiveData = MutableLiveData<EmptyState>()

    init {
        getCartList()
    }

    fun removeCartItem(cartItem: CartItem): Completable {
        return cartRepository.removeCartItem(cartItem.product_id).doAfterSuccess {
            val cartItems: MutableList<CartItem> = cartResponseLiveData.value!!.message as MutableList<CartItem>
            val index = cartItems.indexOf(cartItem)
            cartItems.removeAt(index)

            cartResponseLiveData.value!!.message = cartItems

            val count = EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
            count?.let {
                it.count -= cartItem.count.toInt()
                EventBus.getDefault().postSticky(it)
            }
            calculatePrice()

            cartResponseLiveData?.let {
                if(it.value?.message!!.isEmpty()){
                    emptyStateLiveData.postValue(EmptyState(true, R.string.empty_state_empty_cart))
                }
            }

        }.ignoreElement()
    }

    fun changeCartItemCount(cartItem: CartItem, count: Int, isIncrease: Boolean): Completable {
        return cartRepository.changeCartItemCount(cartItem.product_id, count).doAfterSuccess {
            val cartItemCount = EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
            cartItemCount?.let {
                if (isIncrease) {
                    it.count += 1
                    EventBus.getDefault().postSticky(it)
                } else {
                    it.count -= 1
                    EventBus.getDefault().postSticky(it)
                }

            }
            val cartItems: MutableList<CartItem> =
                cartResponseLiveData.value!!.message as MutableList<CartItem>
            val index = cartItems.indexOf(cartItem)
            cartItem.count = count.toString()
            cartItems.set(index, cartItem)

            cartResponseLiveData.value!!.message = cartItems

            calculatePrice()
        }.ignoreElement()
    }

    private fun calculatePrice() {
        cartResponseLiveData.value?.let { cartResponseLiveData ->
            totalPriceLiveData.value?.let { tp ->
                var totalPrice = 0
                cartResponseLiveData.message.forEach { cartItem ->
                    Log.i("LOG", "size: ${cartResponseLiveData.message.size}")
                    totalPrice += cartItem.price.toInt() * cartItem.count.toInt()
                }
                totalPriceLiveData.postValue(totalPrice)
                Log.i("LOG", "totalPrice: ${totalPrice}")
                Log.i("LOG", "checkTotalPrice: ${totalPriceLiveData.value}")
            }
        }
    }

     fun getCartList() {
        if (!TokenHolder.token.isNullOrEmpty()) {
            progressBarLiveData.value = true
            emptyStateLiveData.value=EmptyState(false)
            cartRepository.getBasketList()
                .singleHelper()
                .doFinally { progressBarLiveData.value = false }
                .subscribe(object : MySingleObserver<CartResponse>(compositeDisposable) {
                    override fun onSuccess(t: CartResponse) {
                        if(!t.message.isNullOrEmpty()){
                            cartResponseLiveData.value = t
                            totalPriceLiveData.value = t.totalPrice
                        }else{
                            emptyStateLiveData.value= EmptyState(true,R.string.empty_state_empty_cart)
                        }
                    }

                })
        }else{
            emptyStateLiveData.value=EmptyState(true, R.string.empty_state_signUp,true)
        }

    }

}