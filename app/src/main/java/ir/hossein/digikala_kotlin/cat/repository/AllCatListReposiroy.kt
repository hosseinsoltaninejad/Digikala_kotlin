package ir.hossein.digikala_kotlin.cat.repository

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.AllCatListItem

interface AllCatListReposiroy {
    fun getAllCatList():Single<List<AllCatListItem>>
}