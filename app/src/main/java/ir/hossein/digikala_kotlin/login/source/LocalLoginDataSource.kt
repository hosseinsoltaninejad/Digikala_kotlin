package ir.hossein.digikala_kotlin.login.source

import android.content.SharedPreferences
import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.LoginMessage
import ir.hossein.digikala_kotlin.login.TokenHolder

class LocalLoginDataSource(private val sharedPreferences: SharedPreferences) : LoginDataSource {
    override fun login(email: String, pass: String): Single<LoginMessage> {
        TODO("Not yet implemented")
    }
    override fun register(email: String, pass: String): Single<LoginMessage> {
        TODO("Not yet implemented")
    }
    override fun saveToken(token: String, refreshToken: String) {
        sharedPreferences.edit().apply {
            putString("token", token)
            putString("refreshToken", refreshToken)
        }.apply()
    }
    override fun loadToken() {
        TokenHolder.updateToken(
            sharedPreferences.getString("token", null),
            sharedPreferences.getString("refreshToken", null)
        )
    }

    override fun checkLogin(): Boolean {
        if(sharedPreferences.getString("token","")!=""){
            return true
        }else{
            return false
        }
    }

    override fun addToFav(id:String): Single<LoginMessage> {
        TODO("Not yet implemented")
    }

    override fun deleteFav(id: String): Single<LoginMessage> {
        TODO("Not yet implemented")
    }

}