package ir.hossein.digikala_kotlin.detail.compare.dataSource

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.ComparableProduct

interface ComparableDataSource {
    fun getComparableProduct(cat:String):Single<List<ComparableProduct>>
}