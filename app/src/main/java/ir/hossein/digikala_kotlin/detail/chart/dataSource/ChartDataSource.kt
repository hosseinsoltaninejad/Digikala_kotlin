package ir.hossein.digikala_kotlin.detail.chart.dataSource

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.PriceHistory

interface ChartDataSource {
    fun getPriceHistory(id:String): Single<List<PriceHistory>>
}