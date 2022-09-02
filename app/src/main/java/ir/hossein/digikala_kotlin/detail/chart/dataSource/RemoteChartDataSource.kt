package ir.hossein.digikala_kotlin.detail.chart.dataSource

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.PriceHistory
import ir.hossein.digikala_kotlin.retrofit.ApiService

class RemoteChartDataSource(private val apiService: ApiService): ChartDataSource {
    override fun getPriceHistory(id:String): Single<List<PriceHistory>> = apiService.getPriceHidtory(id)
}