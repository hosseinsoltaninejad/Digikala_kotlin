package ir.hossein.digikala_kotlin.retrofit

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.*
import ir.hossein.digikala_kotlin.login.TokenHolder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {


    @GET("readbanner.php")
    fun getBanners(): Single<List<Banner>>

    @GET("getcat.php")
    fun getCats(): Single<List<Cat>>

    @GET("readamazing.php")
    fun getProduct(): Single<List<Product>>

   @GET("getdetail2.php")
     fun getDetail(@Query("id") id: String, @Query("user") user: String): Single<List<Detail>>

   @GET("properties.php")
     fun getProperties(): Single<List<Property>>

     @GET("history.php")
     fun getPriceHidtory(@Query("id") id: String): Single<List<PriceHistory>>

     @GET("getcomparelist.php")
     fun getComparableList(@Query("cat") cat: String): Single<List<ComparableProduct>>

     @FormUrlEncoded
     @POST("register.php")
     fun register(@Field("email") email: String, @Field("pass") pass: String): Single<LoginMessage>

     @FormUrlEncoded
     @POST("login2.php")
     fun login(@Field("email") email: String, @Field("pass") pass: String): Single<LoginMessage>

     @GET("addfavorite.php")
     fun addToFav(@Query("id") id: String): Single<LoginMessage>

     @GET("deletefav2.php")
     fun deleteFav(@Query("id") id: String): Single<LoginMessage>

     @GET("getcatlist.php")
     fun getAllCatList():Single<List<AllCatListItem>>

     @GET("getbasket.php")
     fun getBasketList():Single<CartResponse>

     @GET("addbasket.php")
     fun addToBasket(@Query("productId") id: String):Single<LoginMessage>

     @GET("changecartitemcount.php")
     fun changeCartItemCount(@Query("productId") id: String,@Query("count") count: Int):Single<LoginMessage>

     @GET("removecartitem.php")
     fun removeCartItem(@Query("id") id: String):Single<LoginMessage>

     @GET("getcartitemcount.php")
     fun getCartItemCount():Single<CartItemCount>

     @GET("checkout2.php")
     fun checkout(@Query("order_id") orderId:String):Single<LoginMessage>
}

fun getClient(): ApiService {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val oldRequest = it.request()
            val newRequestBuilder = oldRequest.newBuilder()
            if (TokenHolder.token != null) {
                newRequestBuilder.addHeader("token", TokenHolder.token)
            }
            newRequestBuilder.addHeader("Accept", "application/json")
            newRequestBuilder.method(oldRequest.method(), oldRequest.body())
            return@addInterceptor it.proceed(newRequestBuilder.build())
        }
        .build()
    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.104:8080/digikala/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    return retrofit.create(ApiService::class.java)
}