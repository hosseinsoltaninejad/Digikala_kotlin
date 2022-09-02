package ir.hossein.digikala_kotlin.home.repo

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.Product

interface ProductRepository {
    fun getProduct() : Single<List<Product>>
}