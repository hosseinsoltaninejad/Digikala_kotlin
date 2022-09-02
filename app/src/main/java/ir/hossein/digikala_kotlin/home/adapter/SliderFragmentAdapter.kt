package ir.hossein.digikala_kotlin.home.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ir.hossein.digikala_kotlin.data.Banner
import ir.hossein.digikala_kotlin.home.FragmentSlider

class SliderFragmentAdapter(private val fragment: Fragment , private val banners : List<Banner>) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = banners.size

    override fun createFragment(position: Int): Fragment = FragmentSlider.newInstance(banners[position])
}