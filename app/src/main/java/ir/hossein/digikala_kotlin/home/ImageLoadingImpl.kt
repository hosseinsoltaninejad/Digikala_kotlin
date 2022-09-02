package ir.hossein.digikala_kotlin.home

import com.facebook.drawee.view.SimpleDraweeView
import ir.hossein.digikala_kotlin.costum.MyImageView

class ImageLoadingImpl : ImageLoading {
    override fun load(imageView: MyImageView, imageUrl: String) {
        if (imageView is SimpleDraweeView){
            imageView.setImageURI(imageUrl)
        }
    }
}