package ir.hossein.digikala_kotlin.home.source

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.Banner

class LocalBannerDataSource : BannerDataSource {
    override fun getBannerts(): Single<List<Banner>> {
        TODO("Not yet implemented")
    }
}