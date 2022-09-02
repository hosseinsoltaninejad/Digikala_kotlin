package ir.hossein.digikala_kotlin.detail.repository

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.Property
import ir.hossein.digikala_kotlin.detail.dataSource.PropertiesDataSource


class PropertiesRepositoryImpl(private val propertiesDataSource: PropertiesDataSource):
    PropertiesRepository {
    override fun getProperties(): Single<List<Property>> = propertiesDataSource.getProperties()
}