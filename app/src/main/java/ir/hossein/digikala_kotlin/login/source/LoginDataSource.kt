package ir.hossein.digikala_kotlin.login.source

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.LoginMessage

interface LoginDataSource {
    fun login(email:String,pass:String):Single<LoginMessage>
    fun register(email:String,pass:String):Single<LoginMessage>
    fun saveToken(token:String,refreshToken:String)
    fun loadToken()
    fun checkLogin():Boolean
    fun addToFav(id:String):Single<LoginMessage>
    fun deleteFav(id:String):Single<LoginMessage>
}