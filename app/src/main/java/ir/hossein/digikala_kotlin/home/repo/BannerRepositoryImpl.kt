package ir.hossein.digikala_kotlin.home.repo

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.Banner
import ir.hossein.digikala_kotlin.home.repo.BannerRepository
import ir.hossein.digikala_kotlin.home.source.BannerDataSource

class BannerRepositoryImpl(
    private val remoteBannerDataSource: BannerDataSource,
    private val localBannerDataSource: BannerDataSource
) : BannerRepository {
    override fun getBannerts(): Single<List<Banner>> = remoteBannerDataSource.getBannerts()
}