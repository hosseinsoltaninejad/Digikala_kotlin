package ir.hossein.digikala_kotlin.login

object TokenHolder {
    var token:String?=null
    private set

    var refreshToken:String?=null
    private set

    fun updateToken(token:String?,refreshToken:String?){
        TokenHolder.token =token
        TokenHolder.refreshToken =refreshToken
    }
}