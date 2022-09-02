package ir.hossein.digikala_kotlin.detail.dataSource

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.Property
import ir.hossein.digikala_kotlin.retrofit.ApiService

class RemotePreopertiesDataSource(private val apiService: ApiService): PropertiesDataSource {
    override fun getProperties(): Single<List<Property>> = apiService.getProperties()
}