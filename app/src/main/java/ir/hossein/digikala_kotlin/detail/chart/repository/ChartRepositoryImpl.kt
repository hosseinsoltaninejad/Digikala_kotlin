package ir.hossein.digikala_kotlin.detail.chart.repository

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.PriceHistory
import ir.hossein.digikala_kotlin.detail.chart.dataSource.ChartDataSource

class ChartRepositoryImpl(private val remoteChartDataSource: ChartDataSource): ChartRepository {
    override fun getPriceHistory(id:String): Single<List<PriceHistory>> = remoteChartDataSource.getPriceHistory(id)

}