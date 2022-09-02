package ir.hossein.digikala_kotlin.detail.dataSource

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.Detail
import ir.hossein.digikala_kotlin.data.LoginMessage
import ir.hossein.digikala_kotlin.retrofit.ApiService


class RemoteDetailDataSource(private val apiService: ApiService): DetailDataSource {
    override fun getDetail(id:String,user:String): Single<List<Detail>> = apiService.getDetail(id,user)
    override fun addToBasket(id: String): Single<LoginMessage> =apiService.addToBasket(id)
}