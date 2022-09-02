package ir.hossein.digikala_kotlin.home.source

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.Banner
import ir.hossein.digikala_kotlin.retrofit.ApiService

class RemoteBannerDataSource (private val  apiService: ApiService): BannerDataSource {
    override fun getBannerts(): Single<List<Banner>> = apiService.getBanners()
}