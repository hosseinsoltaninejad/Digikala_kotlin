package ir.hossein.digikala_kotlin.checkout

import androidx.lifecycle.MutableLiveData
import ir.hossein.digikala_kotlin.checkout.repo.CheckoutRepository
import ir.hossein.digikala_kotlin.data.LoginMessage
import ir.hossein.digikala_kotlin.utils.BaseViewModel
import ir.hossein.digikala_kotlin.utils.MySingleObserver
import ir.hossein.digikala_kotlin.utils.singleHelper

class CheckoutViewModel(checkoutRepository: CheckoutRepository, orderId:String): BaseViewModel() {
    val checkoutLiveData=MutableLiveData<LoginMessage>()
    init {
        progressBarLiveData.value=true
        checkoutRepository.checkout(orderId)
            .singleHelper()
            .doFinally { progressBarLiveData.value=false }
            .subscribe(object : MySingleObserver<LoginMessage>(compositeDisposable){
                override fun onSuccess(t: LoginMessage) {
                    checkoutLiveData.value=t
                }
            })

    }
}