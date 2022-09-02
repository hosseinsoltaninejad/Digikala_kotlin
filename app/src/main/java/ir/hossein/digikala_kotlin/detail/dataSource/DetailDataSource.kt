package ir.hossein.digikala_kotlin.detail.dataSource

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.Detail
import ir.hossein.digikala_kotlin.data.LoginMessage

interface DetailDataSource {
    fun getDetail(id:String,user:String): Single<List<Detail>>
    fun addToBasket(id:String):Single<LoginMessage>
}