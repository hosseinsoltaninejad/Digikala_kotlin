package ir.hossein.digikala_kotlin.detail.viewModel

import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.Detail
import ir.hossein.digikala_kotlin.data.Fav
import ir.hossein.digikala_kotlin.data.LoginMessage
import ir.hossein.digikala_kotlin.detail.repository.DetailRepository
import ir.hossein.digikala_kotlin.login.TokenHolder
import ir.hossein.digikala_kotlin.login.repository.LoginRepository
import ir.hossein.digikala_kotlin.utils.BaseViewModel
import ir.hossein.digikala_kotlin.utils.MySingleObserver
import ir.hossein.digikala_kotlin.utils.singleHelper

class DetailViewModel(val id:String, private val detailRepository: DetailRepository, private val loginRepository: LoginRepository): BaseViewModel() {
    val detailLiveData=MutableLiveData<List<Detail>>()
    val detailFavLiveData=MutableLiveData<List<Fav>>()
    var intentIdLiveData=MutableLiveData<String>()
    init {
        progressBarLiveData.value=true
        intentIdLiveData.value=id
        loginRepository.loadToken()
        val token=TokenHolder.token
        if(token==null){
            detailRepository.getDetail(intentIdLiveData.value!!,"")
                .singleHelper()
                .doFinally {
                    progressBarLiveData.value=false
                }
                .subscribe(object :MySingleObserver<List<Detail>>(compositeDisposable) {
                    override fun onSuccess(t: List<Detail>) {
                        detailLiveData.value=t
                        detailFavLiveData.value=t[0].fav
                    }
                })
        }else{
            detailRepository.getDetail(intentIdLiveData.value!!, TokenHolder.token!!)
                .singleHelper()
                .doFinally {
                    progressBarLiveData.value=false
                }
                .subscribe(object : MySingleObserver<List<Detail>>(compositeDisposable) {
                    override fun onSuccess(t: List<Detail>) {
                        detailLiveData.value=t
                        detailFavLiveData.value=t[0].fav
                    }
                })
        }
    }

    fun addToBasket(id: String):Single<LoginMessage>{
        return  detailRepository.addToBasket(id)
    }


}