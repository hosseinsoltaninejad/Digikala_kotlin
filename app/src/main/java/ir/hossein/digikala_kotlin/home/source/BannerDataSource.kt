package ir.hossein.digikala_kotlin.home.source

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.Banner

interface BannerDataSource {

    fun getBannerts () : Single<List<Banner>>
}