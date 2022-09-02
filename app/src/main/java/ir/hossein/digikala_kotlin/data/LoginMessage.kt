package ir.hossein.digikala_kotlin.data

data class LoginMessage(
    val message: String,
    val refresh_token: String,
    val status: String
)