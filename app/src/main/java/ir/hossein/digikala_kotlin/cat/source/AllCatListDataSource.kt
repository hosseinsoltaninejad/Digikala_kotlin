package ir.hossein.digikala_kotlin.cat.source

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.AllCatListItem

interface AllCatListDataSource {
    fun getAllCatList():Single<List<AllCatListItem>>
}