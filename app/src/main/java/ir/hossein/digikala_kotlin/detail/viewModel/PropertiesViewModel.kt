package ir.hossein.digikala_kotlin.detail.viewModel

import androidx.lifecycle.MutableLiveData
import ir.hossein.digikala_kotlin.data.Property
import ir.hossein.digikala_kotlin.detail.repository.PropertiesRepository
import ir.hossein.digikala_kotlin.utils.BaseViewModel
import ir.hossein.digikala_kotlin.utils.MySingleObserver
import ir.hossein.digikala_kotlin.utils.singleHelper

class PropertiesViewModel(private val propertiesRepository: PropertiesRepository): BaseViewModel() {
    val propertiesLiveData= MutableLiveData<List<Property>>()
    init {
        progressBarLiveData.value=true
        propertiesRepository.getProperties()
            .doFinally {
                progressBarLiveData.postValue(false)
            }
            .singleHelper()
            .subscribe(object : MySingleObserver<List<Property>>(compositeDisposable){
                override fun onSuccess(t: List<Property>) {
                    propertiesLiveData.value=t
                }
            })
    }
}