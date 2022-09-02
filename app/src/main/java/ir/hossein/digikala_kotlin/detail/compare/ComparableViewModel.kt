package ir.hossein.digikala_kotlin.detail.compare

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import ir.hossein.digikala_kotlin.data.ComparableProduct
import ir.hossein.digikala_kotlin.detail.compare.repository.ComparableRepository
import ir.hossein.digikala_kotlin.utils.BaseViewModel
import ir.hossein.digikala_kotlin.utils.MySingleObserver
import ir.hossein.digikala_kotlin.utils.singleHelper
import org.clicksite.digikala_kotlin.utils.*

class ComparableViewModel(bundle:Bundle, comparableRepository: ComparableRepository) :
    BaseViewModel() {

    val comparableListLiveData = MutableLiveData<List<ComparableProduct>>()
    var firstProductId:String?=null
    var firstProductImage:String?=null
    var firstProductTitle:String?=null
    var firstProductPrice:String?=null

        init {
            progressBarLiveData.value=true
            val cat=bundle.getString(DATA)
            firstProductId=bundle.getString(FIRST_PRODUCT)
            firstProductTitle=bundle.getString(PRODUCT_TITLE)
            firstProductPrice=bundle.getString(PRODUCT_PRICE)
            firstProductImage=bundle.getString(PRODUCT_IMG)
            comparableRepository.getComparableProduct(cat!!)
                .singleHelper()
                .doFinally {
                    progressBarLiveData.value=false
                }
                .subscribe(object: MySingleObserver<List<ComparableProduct>>(compositeDisposable){
                    override fun onSuccess(t: List<ComparableProduct>) {
                        comparableListLiveData.value=t
                    }

                })
        }

}