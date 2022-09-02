package ir.hossein.digikala_kotlin.detail.repository

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.Detail
import ir.hossein.digikala_kotlin.data.LoginMessage
import ir.hossein.digikala_kotlin.detail.dataSource.DetailDataSource

class DetailRepositoryImpl(private val remoteDetailDataSource: DetailDataSource): DetailRepository {
    override fun getDetail(id:String,user:String): Single<List<Detail>> = remoteDetailDataSource.getDetail(id, user)
    override fun addToBasket(id: String): Single<LoginMessage> = remoteDetailDataSource.addToBasket(id)
}