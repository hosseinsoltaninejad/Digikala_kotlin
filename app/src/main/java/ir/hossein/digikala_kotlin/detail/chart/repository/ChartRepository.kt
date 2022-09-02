package ir.hossein.digikala_kotlin.detail.chart.repository

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.PriceHistory

interface ChartRepository {
    fun getPriceHistory(id:String):Single<List<PriceHistory>>
}