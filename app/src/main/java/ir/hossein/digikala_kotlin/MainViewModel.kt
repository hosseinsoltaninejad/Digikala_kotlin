package ir.hossein.digikala_kotlin

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ir.hossein.digikala_kotlin.cart.repo.CartRepository
import ir.hossein.digikala_kotlin.data.CartItemCount
import ir.hossein.digikala_kotlin.login.TokenHolder
import ir.hossein.digikala_kotlin.utils.BaseViewModel
import ir.hossein.digikala_kotlin.utils.MySingleObserver
import org.greenrobot.eventbus.EventBus

class MainViewModel(private val cartRepository: CartRepository) : BaseViewModel() {
    fun getCartItemCount() {
        if (!TokenHolder.token.isNullOrEmpty()) {
            cartRepository.getCartItemCount()
                .subscribeOn(Schedulers.io())
                .subscribe(object : MySingleObserver<CartItemCount>(compositeDisposable) {
                    override fun onSuccess(t: CartItemCount) {
                        EventBus.getDefault().postSticky(t)
                    }
                })
        }
    }
}