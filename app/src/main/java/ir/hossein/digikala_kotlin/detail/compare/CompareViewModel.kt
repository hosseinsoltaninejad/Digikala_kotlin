package ir.hossein.digikala_kotlin.detail.compare

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import ir.hossein.digikala_kotlin.data.Detail
import ir.hossein.digikala_kotlin.data.Property
import ir.hossein.digikala_kotlin.detail.repository.DetailRepository
import ir.hossein.digikala_kotlin.detail.repository.PropertiesRepository
import ir.hossein.digikala_kotlin.utils.BaseViewModel
import ir.hossein.digikala_kotlin.utils.MySingleObserver
import ir.hossein.digikala_kotlin.utils.singleHelper
import org.clicksite.digikala_kotlin.utils.*

class CompareViewModel(propertiesRepository: PropertiesRepository, detailRepository: DetailRepository, bundle:Bundle): BaseViewModel() {
    val firstProductPropertiesLiveData= MutableLiveData<List<Property>>()
    val secondProductPropertiesLiveData= MutableLiveData<List<Property>>()
    val secondDetailLiveData= MutableLiveData<List<Detail>>()
    var firstId:String?=null
    var firstImage:String?=null
    var firstPrice:String?=null
    var firstTitle:String?=null
    var secondId:String?=null
    init {
        firstId=bundle.getString(PRODUCT_ID)
        firstImage=bundle.getString(PRODUCT_IMG)
        firstPrice=bundle.getString(PRODUCT_PRICE)
        firstTitle=bundle.getString(PRODUCT_TITLE)
        secondId=bundle.getString(SECOND_PRODUCT)
        progressBarLiveData.value=true

        detailRepository.getDetail(secondId.toString(),"")
            .singleHelper()
            .subscribe(object: MySingleObserver<List<Detail>>(compositeDisposable){
                override fun onSuccess(t: List<Detail>) {
                    secondDetailLiveData.value=t
                }
            })

        propertiesRepository.getProperties()
            .singleHelper()
            .doFinally {
                progressBarLiveData.value=false
            }
            .subscribe(object:MySingleObserver<List<Property>>(compositeDisposable){
                override fun onSuccess(t: List<Property>) {
                    firstProductPropertiesLiveData.value=t
                }

            })

        propertiesRepository.getProperties()
            .singleHelper()
            .doFinally {
                progressBarLiveData.value=false
            }
            .subscribe(object:MySingleObserver<List<Property>>(compositeDisposable){
                override fun onSuccess(t: List<Property>) {
                    secondProductPropertiesLiveData.value=t
                }

            })
    }
}