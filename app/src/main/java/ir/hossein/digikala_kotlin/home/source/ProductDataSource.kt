package ir.hossein.digikala_kotlin.home.source

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.Product

interface ProductDataSource {
    fun getProduct() : Single<List<Product>>
}