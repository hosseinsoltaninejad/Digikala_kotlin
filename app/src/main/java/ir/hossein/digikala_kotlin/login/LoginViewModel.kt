package ir.hossein.digikala_kotlin.login

import androidx.lifecycle.MutableLiveData
import ir.hossein.digikala_kotlin.data.LoginMessage
import ir.hossein.digikala_kotlin.login.repository.LoginRepository
import ir.hossein.digikala_kotlin.utils.BaseViewModel
import ir.hossein.digikala_kotlin.utils.MySingleObserver
import ir.hossein.digikala_kotlin.utils.singleHelper

class LoginViewModel(private val loginRepository: LoginRepository) : BaseViewModel() {
    val loginLiveData = MutableLiveData<LoginMessage>()
    val checkLoginStatus = MutableLiveData<Boolean>()
    val registerLiveData = MutableLiveData<LoginMessage>()
    val favoriteLiveData = MutableLiveData<LoginMessage>()

    fun register(email: String, pass: String) {
        progressBarLiveData.value = true
        loginRepository.register(email, pass)
            .doFinally {
                progressBarLiveData.postValue(false)
            }
            .singleHelper()
            .subscribe(object : MySingleObserver<LoginMessage>(compositeDisposable) {
                override fun onSuccess(t: LoginMessage) {
                    registerLiveData.value = t
                }
            })
    }

    fun login(email: String, pass: String) {
        progressBarLiveData.value = true
        loginRepository.login(email, pass)
            .doFinally {
                progressBarLiveData.postValue(false)
            }
            .singleHelper()
            .subscribe(object : MySingleObserver<LoginMessage>(compositeDisposable) {
                override fun onSuccess(t: LoginMessage) {
                    loginLiveData.value = t
                }
            })
    }

    fun checkLogin() {
        checkLoginStatus.value = loginRepository.checkLogin()
    }

    fun addToFav(id: String) {
        progressBarLiveData.value = true
        loginRepository.addToFav(id).doFinally {
            progressBarLiveData.postValue(false)
        }.singleHelper()
            .subscribe(object : MySingleObserver<LoginMessage>(compositeDisposable) {
                override fun onSuccess(t: LoginMessage) {
                    favoriteLiveData.value = t
                }

            })
    }
    fun deleteFav(id: String) {
        progressBarLiveData.value = true
        loginRepository.deleteFav(id).doFinally {
            progressBarLiveData.postValue(false)
        }.singleHelper()
            .subscribe(object : MySingleObserver<LoginMessage>(compositeDisposable) {
                override fun onSuccess(t: LoginMessage) {
                    favoriteLiveData.value = t
                }
            })
    }
}