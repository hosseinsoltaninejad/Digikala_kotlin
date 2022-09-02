package ir.hossein.digikala_kotlin.detail.compare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ir.hossein.digikala_kotlin.R
import ir.hossein.digikala_kotlin.costum.MyImageView
import ir.hossein.digikala_kotlin.data.ComparableProduct
import ir.hossein.digikala_kotlin.home.ImageLoading

class CompareListAdapter(val imageLoading: ImageLoading): RecyclerView.Adapter<CompareListAdapter.CompareListViewHolder>() {

    var onCompareItemClickListener: OnCompareItemClickListener?=null
    var comparebleProductList=ArrayList<ComparableProduct>()
    set(value) {
        field=value
        notifyDataSetChanged()
    }

    fun setCompareListener(onCompareItemClickListener: OnCompareItemClickListener){
        this.onCompareItemClickListener=onCompareItemClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompareListViewHolder =
        CompareListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.compare_list_item,parent,false))

    override fun onBindViewHolder(holder: CompareListViewHolder, position: Int) {
        val comparableProduct=comparebleProductList[position]
        holder.setData(comparableProduct,imageLoading)
        holder.itemView.setOnClickListener{
            onCompareItemClickListener!!.onItemClick(comparableProduct)
        }
    }

    override fun getItemCount(): Int =comparebleProductList.size


    class CompareListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image= itemView.findViewById<MyImageView>(R.id.img_compareListItem)
        val txtTitle=itemView.findViewById<TextView>(R.id.txt_compareListItem_title)
        val txtPrice=itemView.findViewById<TextView>(R.id.txt_compareListItem_price)

        fun setData(comparableProduct: ComparableProduct,imageLoading: ImageLoading){
            imageLoading.load(image,comparableProduct.image)
            txtTitle.text=comparableProduct.title
            txtPrice.text=comparableProduct.price
        }
    }

    public interface OnCompareItemClickListener{
        fun onItemClick(comparableProduct: ComparableProduct)
    }
}