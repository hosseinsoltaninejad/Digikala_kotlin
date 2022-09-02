package ir.hossein.digikala_kotlin.login.source

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.LoginMessage
import ir.hossein.digikala_kotlin.retrofit.ApiService

class RemoteLoginDataSource(private val apiService: ApiService): LoginDataSource {
    override fun login(email: String, pass: String):Single<LoginMessage> {
        return apiService.login(email, pass)
    }

    override fun register(email: String, pass: String):Single<LoginMessage> {
        return apiService.register(email, pass)
    }

    override fun saveToken(token: String,refreshToken:String) {
        TODO("Not yet implemented")
    }

    override fun loadToken() {
        TODO("Not yet implemented")
    }

    override fun checkLogin(): Boolean {
        TODO("Not yet implemented")
    }

    override fun addToFav(id:String): Single<LoginMessage> = apiService.addToFav(id)
    override fun deleteFav(id: String): Single<LoginMessage> = apiService.deleteFav(id)
}