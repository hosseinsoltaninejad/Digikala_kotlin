package ir.hossein.digikala_kotlin.home.source

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.Product
import ir.hossein.digikala_kotlin.retrofit.ApiService

class RemoteProductDataSource(private val apiService: ApiService) : ProductDataSource {
    override fun getProduct(): Single<List<Product>> = apiService.getProduct()
}