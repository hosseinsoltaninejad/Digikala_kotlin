package ir.hossein.digikala_kotlin.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Banner(
    val id: Int,
    val pic: String,
    val type: Int
) : Parcelable