package ir.hossein.digikala_kotlin.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ir.hossein.digikala_kotlin.R
import ir.hossein.digikala_kotlin.data.Property


const val PROPERTY_ITEM=1
const val PROPERTY_ITEM_CHILD=2
class PropertiesAdapter(private val propretiesList:List<Property>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType== PROPERTY_ITEM){
            val view=LayoutInflater.from(parent.context).inflate(R.layout.properties_item,parent,false)
            PropertiesViewHolder(view)
        }else{
            val view=LayoutInflater.from(parent.context).inflate(R.layout.properties_item_child,parent,false)
            PropertiesChildViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item=propretiesList[position]
        if(getItemViewType(position)== PROPERTY_ITEM){
            (holder as PropertiesViewHolder).txtTitle.text=item.title
        }else{
            (holder as PropertiesChildViewHolder).txtTitle.text=item.title
            (holder as PropertiesChildViewHolder).txtValue.text=item.value
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(propretiesList[position].value == ""){
            PROPERTY_ITEM
        }else{
            PROPERTY_ITEM_CHILD
        }
    }

    override fun getItemCount(): Int =propretiesList.size

    class PropertiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtTitle= itemView.findViewById<TextView>(R.id.txt_propertiesItem_title)!!
    }
    class PropertiesChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtTitle= itemView.findViewById<TextView>(R.id.txt_propertiesChild_title)!!
        val txtValue= itemView.findViewById<TextView>(R.id.txt_propertiesChild_value)!!
    }
}