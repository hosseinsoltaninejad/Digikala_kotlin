package ir.hossein.digikala_kotlin.home.repo

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.Product
import ir.hossein.digikala_kotlin.home.source.ProductDataSource

class ProductRepositoryImpl(private val productDataSource: ProductDataSource): ProductRepository {
    override fun getProduct(): Single<List<Product>>  = productDataSource.getProduct()
}