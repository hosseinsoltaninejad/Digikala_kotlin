package ir.hossein.digikala_kotlin.home.source

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.Cat
import ir.hossein.digikala_kotlin.retrofit.ApiService

class RemoteCatDataSource(private val apiService: ApiService) : CatDataSource {
    override fun getCats(): Single<List<Cat>> = apiService.getCats()
}