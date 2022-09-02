package ir.hossein.digikala_kotlin.home.source

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.Cat

interface CatDataSource {
    fun  getCats() : Single<List<Cat>>
}