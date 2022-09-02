package ir.hossein.digikala_kotlin.utils

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import com.facebook.drawee.backends.pipeline.Fresco
import ir.hossein.digikala_kotlin.MainViewModel
import ir.hossein.digikala_kotlin.cart.CartItemAdapter
import ir.hossein.digikala_kotlin.cart.CartViewModel
import ir.hossein.digikala_kotlin.cart.dataSource.RemoteCartDataSource
import ir.hossein.digikala_kotlin.cart.repo.CartRepository
import ir.hossein.digikala_kotlin.cart.repo.CartRepositoryImpl
import ir.hossein.digikala_kotlin.cat.AllCatListViewModel
import ir.hossein.digikala_kotlin.cat.repository.AllCatListReposiroy
import ir.hossein.digikala_kotlin.cat.repository.AllCatListRepositoryImpl
import ir.hossein.digikala_kotlin.cat.source.RemoteAllCatList
import ir.hossein.digikala_kotlin.data.*
import ir.hossein.digikala_kotlin.detail.adapter.PropertiesAdapter
import ir.hossein.digikala_kotlin.detail.adapter.RatingItemAdapter
import ir.hossein.digikala_kotlin.detail.chart.ChartViewModel
import ir.hossein.digikala_kotlin.detail.chart.dataSource.RemoteChartDataSource
import ir.hossein.digikala_kotlin.detail.chart.repository.ChartRepository
import ir.hossein.digikala_kotlin.detail.chart.repository.ChartRepositoryImpl
import ir.hossein.digikala_kotlin.detail.dataSource.RemoteDetailDataSource
import ir.hossein.digikala_kotlin.detail.dataSource.RemotePreopertiesDataSource
import ir.hossein.digikala_kotlin.detail.repository.DetailRepository
import ir.hossein.digikala_kotlin.detail.repository.DetailRepositoryImpl
import ir.hossein.digikala_kotlin.detail.repository.PropertiesRepository
import ir.hossein.digikala_kotlin.detail.repository.PropertiesRepositoryImpl
import ir.hossein.digikala_kotlin.detail.viewModel.DetailViewModel
import ir.hossein.digikala_kotlin.detail.viewModel.PropertiesViewModel
import ir.hossein.digikala_kotlin.home.*
import ir.hossein.digikala_kotlin.home.adapter.CatAdapter
import ir.hossein.digikala_kotlin.home.adapter.ProductAdapter
import ir.hossein.digikala_kotlin.home.repo.*
import ir.hossein.digikala_kotlin.home.source.LocalBannerDataSource
import ir.hossein.digikala_kotlin.home.source.RemoteBannerDataSource
import ir.hossein.digikala_kotlin.home.source.RemoteCatDataSource
import ir.hossein.digikala_kotlin.home.source.RemoteProductDataSource
import ir.hossein.digikala_kotlin.home.viewmodel.HomeViewModel
import ir.hossein.digikala_kotlin.login.LoginViewModel
import ir.hossein.digikala_kotlin.login.repository.LoginRepository
import ir.hossein.digikala_kotlin.login.repository.LoginRepositoryImpl
import ir.hossein.digikala_kotlin.login.source.LocalLoginDataSource
import ir.hossein.digikala_kotlin.login.source.RemoteLoginDataSource
import ir.hossein.digikala_kotlin.retrofit.getClient
import ir.hossein.digikala_kotlin.cat.AllCatListAdapter
import ir.hossein.digikala_kotlin.checkout.CheckoutViewModel
import ir.hossein.digikala_kotlin.checkout.dataSource.RemoteCheckout
import ir.hossein.digikala_kotlin.checkout.repo.CheckoutRepository
import ir.hossein.digikala_kotlin.checkout.repo.CheckoutRepositoryImpl
import ir.hossein.digikala_kotlin.detail.compare.ComparableViewModel
import ir.hossein.digikala_kotlin.detail.compare.CompareViewModel
import ir.hossein.digikala_kotlin.detail.compare.adapter.CompareListAdapter
import ir.hossein.digikala_kotlin.detail.compare.dataSource.RemoteComparableDataSource
import ir.hossein.digikala_kotlin.detail.compare.repository.ComparableRepository
import ir.hossein.digikala_kotlin.detail.compare.repository.CompareRepositoryImp
import ir.hossein.digikala_kotlin.detail.compare.adapter.CompareProductAdapter
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.android.ext.android.get

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Fresco.initialize(this)

        val myModules = module {
            single<ImageLoading> { ImageLoadingImpl() }
            single { getClient()}
            factory<BannerRepository> { BannerRepositoryImpl(RemoteBannerDataSource(get()), LocalBannerDataSource()) }
           factory<CatRepository> { CatRepositoryImpl(RemoteCatDataSource(get())) }
           factory<ProductRepository> { ProductRepositoryImpl(RemoteProductDataSource(get())) }
            viewModel { HomeViewModel(get(), get () , get()) }
            factory<CatAdapter> { (cats : List<Cat>) -> CatAdapter(cats ,get()) }
            factory<ProductAdapter> { (products : List<Product> ) -> ProductAdapter(products ,get()) }
            factory<DetailRepository> { DetailRepositoryImpl(RemoteDetailDataSource(get())) }
            factory<LoginRepository> { LoginRepositoryImpl(RemoteLoginDataSource(get()), LocalLoginDataSource(get())) }
            viewModel { (id : String ) -> DetailViewModel(id, get (), get()) }
            factory<PropertiesRepository> { PropertiesRepositoryImpl(RemotePreopertiesDataSource(get())) }
            viewModel {  PropertiesViewModel( get ()) }
            factory<PropertiesAdapter> { (propertiesList : List<Property> ) -> PropertiesAdapter(propertiesList) }
            factory<RatingItemAdapter> { (ratingItemsList : List<RatingItem> ) -> RatingItemAdapter(ratingItemsList) }
            factory<ChartRepository> { ChartRepositoryImpl(RemoteChartDataSource(get())) }
            viewModel { (id : String ) -> ChartViewModel(id, get ()) }
            factory<ComparableRepository> { CompareRepositoryImp(RemoteComparableDataSource(get())) }
            viewModel { (bundle: Bundle) -> ComparableViewModel(bundle, get()) }
            factory { CompareListAdapter(get()) }
            viewModel { (bundle: Bundle) -> CompareViewModel(get(), DetailRepositoryImpl(RemoteDetailDataSource(get())), bundle) }
            factory { CompareProductAdapter() }
            single<SharedPreferences> { this@App.getSharedPreferences("user_data", MODE_PRIVATE) }
            viewModel { LoginViewModel(get()) }
            factory<AllCatListReposiroy> { AllCatListRepositoryImpl(RemoteAllCatList(get())) }
            viewModel { AllCatListViewModel(get()) }
            factory<AllCatListAdapter> { (productList : List<ProductX>) -> AllCatListAdapter(productList , get()) }
            factory<CartItemAdapter> { (cartResponse : CartResponse) -> CartItemAdapter(cartResponse , get()) }
            factory<CartRepository> { CartRepositoryImpl(RemoteCartDataSource(get())) }
            viewModel { CartViewModel(get()) }
            viewModel { MainViewModel(get()) }
            factory<CheckoutRepository> { CheckoutRepositoryImpl(RemoteCheckout(get())) }
            viewModel { (orderId:String)->CheckoutViewModel(get(),orderId) }
        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }

        val loginRepository: LoginRepository = get()
        loginRepository.loadToken()
    }
}