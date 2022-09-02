package ir.hossein.digikala_kotlin.home.repo

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.Cat
import ir.hossein.digikala_kotlin.home.source.CatDataSource
import ir.hossein.digikala_kotlin.home.source.RemoteCatDataSource

class CatRepositoryImpl(private val  remoteCatDataSource: CatDataSource) : CatRepository {
    override fun getCats(): Single<List<Cat>> = remoteCatDataSource.getCats()
}