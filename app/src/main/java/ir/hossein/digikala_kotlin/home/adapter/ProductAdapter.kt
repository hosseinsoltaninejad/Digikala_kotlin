package ir.hossein.digikala_kotlin.home.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ir.hossein.digikala_kotlin.R
import ir.hossein.digikala_kotlin.costum.MyImageView
import ir.hossein.digikala_kotlin.data.Product
import ir.hossein.digikala_kotlin.home.ImageLoading

const val PRODUCT_ITEM_DEFAULT = 1
const val PRODUCT_ITEM = 2

class ProductAdapter(private val products: List<Product>, private val imageLoading: ImageLoading) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onProductItemClickListener: OnProductItemClickListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == PRODUCT_ITEM_DEFAULT) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item_def, parent, false)
            return ProdcutViewHolderDef(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
            return ProductViewHolder(view)
        }

    }

    fun setProductListener(onProductItemClickListener: OnProductItemClickListener){
        this.onProductItemClickListener=onProductItemClickListener
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == PRODUCT_ITEM_DEFAULT) {
            (holder as ProdcutViewHolderDef).image.setImageResource(R.drawable.produt_item_image)
        } else {
            val product = products[position - 1]
            imageLoading.load((holder as ProductViewHolder).image, product.image)
            (holder as ProductViewHolder).txtTitle.text = product.title
            (holder as ProductViewHolder).txtPrice.text = product.price
            (holder as ProductViewHolder).txtPrePrice.text = product.pprice
            (holder as ProductViewHolder).txtPrePrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            holder.itemView.setOnClickListener{
                onProductItemClickListener?.onProductClick(product)
            }
        }

    }

    override fun getItemCount(): Int = (products.size) + 1

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            PRODUCT_ITEM_DEFAULT
        } else {
            PRODUCT_ITEM
        }
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitle = itemView.findViewById<TextView>(R.id.txt_productItem_title)
        val txtPrice = itemView.findViewById<TextView>(R.id.txt_productItem_price)
        val txtPrePrice = itemView.findViewById<TextView>(R.id.txt_productItem_prePrice)
        val image = itemView.findViewById<MyImageView>(R.id.img_productItem)

    }

    class ProdcutViewHolderDef(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<MyImageView>(R.id.img_productItemDef)

    }

    interface OnProductItemClickListener {
        fun onProductClick(product: Product)
    }

}