package ir.hossein.digikala_kotlin.home

import ir.hossein.digikala_kotlin.costum.MyImageView

interface ImageLoading {
    fun load(imageView: MyImageView , imageUrl : String)
}