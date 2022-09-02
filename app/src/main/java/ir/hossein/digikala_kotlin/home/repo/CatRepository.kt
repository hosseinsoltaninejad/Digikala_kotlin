package ir.hossein.digikala_kotlin.home.repo

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.Cat

interface CatRepository {
    fun  getCats() : Single<List<Cat>>
}