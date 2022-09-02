package ir.hossein.digikala_kotlin.detail.compare.repository

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.ComparableProduct

interface ComparableRepository {
    fun getComparableProduct(cat:String):Single<List<ComparableProduct>>
}