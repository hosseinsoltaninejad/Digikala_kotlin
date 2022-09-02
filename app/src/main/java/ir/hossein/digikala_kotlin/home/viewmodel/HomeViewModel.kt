package ir.hossein.digikala_kotlin.home.viewmodel

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ir.hossein.digikala_kotlin.data.Banner
import ir.hossein.digikala_kotlin.data.Cat
import ir.hossein.digikala_kotlin.data.Product
import ir.hossein.digikala_kotlin.home.repo.BannerRepository
import ir.hossein.digikala_kotlin.home.repo.CatRepository
import ir.hossein.digikala_kotlin.home.repo.ProductRepository
import ir.hossein.digikala_kotlin.utils.BaseViewModel
import ir.hossein.digikala_kotlin.utils.MySingleObserver
import ir.hossein.digikala_kotlin.utils.singleHelper

class HomeViewModel( bannerRepository: BannerRepository,  catRepository: CatRepository, productRepository: ProductRepository) : BaseViewModel() {
    val bannerLiveData = MutableLiveData<List<Banner>>()
    val catLiveData = MutableLiveData<List<Cat>>()
    val productLiveData = MutableLiveData<List<Product>>()
    init {
        progressBarLiveData.value = true
        bannerRepository.getBannerts()
            .singleHelper()
            .subscribe(object : MySingleObserver<List<Banner>>(compositeDisposable){
                override fun onSuccess(t: List<Banner>) {
                  bannerLiveData.value = t
                }
            })

        catRepository.getCats()
            .singleHelper()
            .subscribe(object : MySingleObserver<List<Cat>>(compositeDisposable){
                override fun onSuccess(t: List<Cat>) {
                    catLiveData.value = t
                }
            })

        productRepository.getProduct()
            .singleHelper()
            .doFinally {
                progressBarLiveData.value = false
            }
            .subscribe(object : MySingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                    productLiveData.value = t
                }

            })
    }
}