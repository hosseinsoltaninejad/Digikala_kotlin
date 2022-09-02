package ir.hossein.digikala_kotlin.cat.source

import io.reactivex.Single
import ir.hossein.digikala_kotlin.cat.source.AllCatListDataSource
import ir.hossein.digikala_kotlin.data.AllCatListItem
import ir.hossein.digikala_kotlin.retrofit.ApiService

class RemoteAllCatList(private val apiService: ApiService): AllCatListDataSource {
    override fun getAllCatList(): Single<List<AllCatListItem>> = apiService.getAllCatList()
}