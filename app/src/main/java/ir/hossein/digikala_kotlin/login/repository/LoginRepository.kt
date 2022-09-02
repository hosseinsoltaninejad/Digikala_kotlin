package ir.hossein.digikala_kotlin.login.repository

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.LoginMessage

interface LoginRepository {
    fun login(email:String,pass:String):Single<LoginMessage>
    fun register(email:String,pass:String):Single<LoginMessage>
    fun checkLogin():Boolean
    fun addToFav(id:String):Single<LoginMessage>
    fun deleteFav(id:String):Single<LoginMessage>
    fun loadToken()

}