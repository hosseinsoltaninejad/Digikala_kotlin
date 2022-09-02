package ir.hossein.digikala_kotlin.cat.repository

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.AllCatListItem
import ir.hossein.digikala_kotlin.cat.source.AllCatListDataSource

class AllCatListRepositoryImpl(private val remoteAllCatList: AllCatListDataSource) : AllCatListReposiroy {
    override fun getAllCatList(): Single<List<AllCatListItem>> = remoteAllCatList.getAllCatList()
}