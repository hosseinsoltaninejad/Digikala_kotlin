package ir.hossein.digikala_kotlin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ir.hossein.digikala_kotlin.R
import ir.hossein.digikala_kotlin.costum.MyImageView
import ir.hossein.digikala_kotlin.data.Banner
import org.koin.android.ext.android.inject

class FragmentSlider : Fragment() {
    val  imageLoading : ImageLoading by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_slider , container , false)
    }

    companion object {
        fun newInstance(banner: Banner) : FragmentSlider {
         return   FragmentSlider().apply {
                arguments = Bundle().apply {
                    putParcelable("banner" , banner)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val banner = arguments?.getParcelable<Banner>("banner")
        imageLoading.load(view as MyImageView, banner!!.pic)
    }
}