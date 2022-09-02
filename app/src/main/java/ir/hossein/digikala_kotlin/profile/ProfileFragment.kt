package ir.hossein.digikala_kotlin.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ir.hossein.digikala_kotlin.databinding.FragmentProfileBinding
import ir.hossein.digikala_kotlin.home.viewmodel.HomeViewModel
import ir.hossein.digikala_kotlin.login.LoginViewModel
import ir.hossein.digikala_kotlin.utils.MyFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : MyFragment() {
    private lateinit var binding : FragmentProfileBinding
    private val loginViewModel : LoginViewModel by viewModel ()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel.checkLogin()

        loginViewModel.checkLoginStatus.observe(viewLifecycleOwner){
            if(it){
                binding.constraintProfileLogin.visibility=View.GONE
                binding.constraintProfileProfile.visibility=View.VISIBLE
            }else{
                binding.constraintProfileLogin.visibility=View.VISIBLE
                binding.constraintProfileProfile.visibility=View.GONE
            }
        }
    }
}