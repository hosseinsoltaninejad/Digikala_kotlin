package ir.hossein.digikala_kotlin.detail.repository

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.Property

interface PropertiesRepository {
    fun getProperties():Single<List<Property>>
}