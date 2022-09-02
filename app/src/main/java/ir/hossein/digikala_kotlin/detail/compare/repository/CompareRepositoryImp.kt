package ir.hossein.digikala_kotlin.detail.compare.repository

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.ComparableProduct
import ir.hossein.digikala_kotlin.detail.compare.dataSource.ComparableDataSource

class CompareRepositoryImp(private val comparableDataSource: ComparableDataSource) :
    ComparableRepository {
    override fun getComparableProduct(cat: String): Single<List<ComparableProduct>> = comparableDataSource.getComparableProduct(cat)
}