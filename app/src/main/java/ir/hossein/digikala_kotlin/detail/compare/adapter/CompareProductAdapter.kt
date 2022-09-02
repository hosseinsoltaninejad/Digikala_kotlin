package ir.hossein.digikala_kotlin.detail.compare.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ir.hossein.digikala_kotlin.R
import ir.hossein.digikala_kotlin.data.Property

const val ITEM_TITLE = 1
const val ITEM_VALUE = 2

class CompareProductAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var firstPropertiesList:ArrayList<Property>?=null
    set(value) {
        field=value
        notifyDataSetChanged()
    }
    var secondPropertiesList:ArrayList<Property>?=null
    set(value) {
        field=value
        notifyDataSetChanged()
    }

    fun removeSecondProduct(){
        secondPropertiesList?.clear()
        notifyDataSetChanged()
    }
    override fun getItemViewType(position: Int): Int {
        return if (firstPropertiesList?.get(position)?.value == "") {
            ITEM_TITLE
        } else {
            ITEM_VALUE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType== ITEM_TITLE){
            CompareTitleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.properties_item,parent,false))
        }else{
            CompareViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.compare_item,parent,false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position)== ITEM_TITLE){
            (holder as CompareTitleViewHolder).txtTitle.text=
                firstPropertiesList?.get(position)?.title
        }else{
            (holder as CompareViewHolder).txtFirst.text="${firstPropertiesList?.get(position)?.title} : ${firstPropertiesList?.get(position)?.value} "
            if(secondPropertiesList.isNullOrEmpty()){
                (holder as CompareViewHolder).txtSecond.text=" - "
            }else{
                (holder as CompareViewHolder).txtSecond.text="${secondPropertiesList?.get(position)?.title} : ${secondPropertiesList?.get(position)?.value} "
            }
        }
    }

    override fun getItemCount(): Int {
        return if(firstPropertiesList.isNullOrEmpty()){
            0
        }else{
            firstPropertiesList!!.size
        }
    }

    class CompareTitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitle = itemView.findViewById<TextView>(R.id.txt_propertiesItem_title)!!
    }

    class CompareViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtFirst = itemView.findViewById<TextView>(R.id.txt_compareItem_first)!!
        val txtSecond = itemView.findViewById<TextView>(R.id.txt_compareItem_second)!!
    }
}