package ir.hossein.digikala_kotlin.detail.compare.dataSource

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.ComparableProduct
import ir.hossein.digikala_kotlin.retrofit.ApiService

class RemoteComparableDataSource(val apiService: ApiService): ComparableDataSource {
    override fun getComparableProduct(cat:String): Single<List<ComparableProduct>> = apiService.getComparableList(cat)
}