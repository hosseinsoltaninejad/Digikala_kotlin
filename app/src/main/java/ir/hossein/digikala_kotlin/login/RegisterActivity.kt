package ir.hossein.digikala_kotlin.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import ir.hossein.digikala_kotlin.databinding.ActivityRegisterBinding
import ir.hossein.digikala_kotlin.utils.MyActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : MyActivity() {
    lateinit var binding: ActivityRegisterBinding
    val loginViewModel:LoginViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener{
            loginViewModel.register(binding.edtRegisterEmail.text.toString() , binding.edtRegisterPass.text.toString())
        }

        loginViewModel.registerLiveData.observe(this){
            Toast.makeText(applicationContext,"با موفقیت وارد حساب کاربری خود شدید¯",Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.txtRegisterLogin.setOnClickListener{
            startActivity(Intent(applicationContext,LoginActivity::class.java))
            finish()
        }
    }
}