package ir.hossein.digikala_kotlin.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import ir.hossein.digikala_kotlin.databinding.ActivityPropertyBinding
import ir.hossein.digikala_kotlin.detail.adapter.PropertiesAdapter
import ir.hossein.digikala_kotlin.detail.viewModel.DetailViewModel
import ir.hossein.digikala_kotlin.detail.viewModel.PropertiesViewModel
import ir.hossein.digikala_kotlin.utils.MyActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PropertyActivity : MyActivity() {
    private lateinit var binding: ActivityPropertyBinding
    private val propertiesViewModel: PropertiesViewModel by viewModel ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPropertyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewProperties.layoutManager = LinearLayoutManager(this)

        propertiesViewModel.propertiesLiveData.observe(this){
            val propertiesAdapter : PropertiesAdapter by inject { parametersOf(it)}
            binding.recyclerViewProperties.adapter = propertiesAdapter
        }

        binding.layoutToolbar.imgToolbarBack.setOnClickListener{
            finish()
        }

        propertiesViewModel.progressBarLiveData.observe(this){
            showProgressBar(it)
        }
    }
}