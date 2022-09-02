package ir.hossein.digikala_kotlin.detail.dataSource

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.Property

interface PropertiesDataSource {
    fun getProperties():Single<List<Property>>
}