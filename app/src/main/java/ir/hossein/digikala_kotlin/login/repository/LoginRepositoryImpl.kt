package ir.hossein.digikala_kotlin.login.repository

import io.reactivex.Single
import ir.hossein.digikala_kotlin.data.LoginMessage
import ir.hossein.digikala_kotlin.login.TokenHolder
import ir.hossein.digikala_kotlin.login.repository.LoginRepository
import ir.hossein.digikala_kotlin.login.source.LoginDataSource

class LoginRepositoryImpl(private val remoteLoginDataSource: LoginDataSource, private val localLoginDataSource: LoginDataSource) : LoginRepository {
    override fun login(email: String, pass: String): Single<LoginMessage> {
        return remoteLoginDataSource.login(email, pass).doOnSuccess {
            TokenHolder.updateToken(it.message, it.refresh_token)
            localLoginDataSource.saveToken(it.message, it.refresh_token)
        }
    }

    override fun register(email: String, pass: String): Single<LoginMessage> {
        return remoteLoginDataSource.register(email, pass).doOnSuccess {
            TokenHolder.updateToken(it.message, it.refresh_token)
            localLoginDataSource.saveToken(it.message, it.refresh_token)
        }
    }

    override fun checkLogin(): Boolean {
        return localLoginDataSource.checkLogin()
    }

    override fun addToFav(id: String): Single<LoginMessage> = remoteLoginDataSource.addToFav(id)
    override fun deleteFav(id: String): Single<LoginMessage> = remoteLoginDataSource.deleteFav(id)
    override fun loadToken() {
        localLoginDataSource.loadToken()
    }


}