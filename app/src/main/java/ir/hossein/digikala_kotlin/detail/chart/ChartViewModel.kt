package ir.hossein.digikala_kotlin.detail.chart

import androidx.lifecycle.MutableLiveData
import ir.hossein.digikala_kotlin.data.PriceHistory
import ir.hossein.digikala_kotlin.detail.chart.repository.ChartRepository
import ir.hossein.digikala_kotlin.utils.BaseViewModel
import ir.hossein.digikala_kotlin.utils.MySingleObserver
import ir.hossein.digikala_kotlin.utils.singleHelper


class ChartViewModel(val id:String, private val chartRepository: ChartRepository): BaseViewModel() {
    val chartLiveData=MutableLiveData<List<PriceHistory>>()
    init {
        chartRepository.getPriceHistory(id)
            .singleHelper()
            .subscribe(object: MySingleObserver<List<PriceHistory>>(compositeDisposable){
                override fun onSuccess(t: List<PriceHistory>) {
                    chartLiveData.value=t
                }
            })
    }
}