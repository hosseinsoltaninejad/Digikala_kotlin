package ir.hossein.digikala_kotlin.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import ir.hossein.digikala_kotlin.databinding.ActivityLoginBinding
import ir.hossein.digikala_kotlin.utils.MyActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : MyActivity() {
    lateinit var binding : ActivityLoginBinding
    val loginViewModel : LoginViewModel by viewModel ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            loginViewModel.login(binding.edtLoginEmail.text.toString() , binding.edtLoginPass.text.toString())
        }

        binding.txtLoginRegister.setOnClickListener {
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
            finish()
        }

        loginViewModel.loginLiveData.observe(this){
            Toast.makeText(applicationContext,"با موفقیت وارد حساب کاربری خود شدید¯",Toast.LENGTH_SHORT).show()
            finish()
        }

        loginViewModel.registerLiveData.observe(this){
            Toast.makeText(applicationContext,"با موفقیت وارد حساب کاربری خود شدید¯",Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}