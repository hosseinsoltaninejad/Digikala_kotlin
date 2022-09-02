package ir.hossein.digikala_kotlin.home.repo

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.Banner

interface BannerRepository {
    fun getBannerts () : Single<List<Banner>>
}