package ir.hossein.digikala_kotlin.home

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.hossein.digikala_kotlin.data.Product
import ir.hossein.digikala_kotlin.databinding.FragmentHomeBinding
import ir.hossein.digikala_kotlin.detail.DetailActivity
import ir.hossein.digikala_kotlin.home.adapter.CatAdapter
import ir.hossein.digikala_kotlin.home.adapter.ProductAdapter
import ir.hossein.digikala_kotlin.home.adapter.SliderFragmentAdapter
import ir.hossein.digikala_kotlin.home.viewmodel.HomeViewModel
import ir.hossein.digikala_kotlin.utils.MyFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeFragment : MyFragment() , ProductAdapter.OnProductItemClickListener {
    private lateinit var binding : FragmentHomeBinding
    private val homeViewModel : HomeViewModel by viewModel ()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewHomeCats.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, true)
        binding.recyclerViewHomeProduct.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, true)

        homeViewModel.bannerLiveData.observe(viewLifecycleOwner){
           val sliderFragmentAdapter = SliderFragmentAdapter(this, it)
            Log.i(TAG, "onViewCreated: ${it.toString()}")
            binding.viewPager.adapter = sliderFragmentAdapter
           binding.dotsIndicator.attachTo(binding.viewPager)
        }

        homeViewModel.progressBarLiveData.observe(viewLifecycleOwner){
            showProgressBar(it)
        }

        homeViewModel.catLiveData.observe(viewLifecycleOwner){
          val catAdapter : CatAdapter by inject { parametersOf(it)}
            binding.recyclerViewHomeCats.adapter = catAdapter
        }

        homeViewModel.productLiveData.observe(viewLifecycleOwner){
            val productAdapter : ProductAdapter by inject { parametersOf(it)}
            binding.recyclerViewHomeProduct.adapter = productAdapter
            productAdapter.setProductListener(this)
        }
    }

    override fun onProductClick(product: Product) {
        startActivity(Intent(context, DetailActivity::class.java).apply {
            putExtra("id",product.id)
        })
    }
}