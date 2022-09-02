package ir.hossein.digikala_kotlin.cat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.hossein.digikala_kotlin.data.ProductX
import ir.hossein.digikala_kotlin.databinding.AllCatListItemBinding
import ir.hossein.digikala_kotlin.home.ImageLoading

class AllCatListAdapter(private val productList:List<ProductX>, private val imageLoading: ImageLoading): RecyclerView.Adapter<AllCatListAdapter.AllCatListViewHolder>() {

    lateinit var binding : AllCatListItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCatListViewHolder {
        binding = AllCatListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllCatListViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: AllCatListViewHolder, position: Int) {
      holder.bindViews(productList[position])
    }

    override fun getItemCount(): Int = productList.size

  inner  class AllCatListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindViews(productX: ProductX){
            imageLoading.load(  binding.imgAllCatListItem , productX.image)
            binding.txtAllCatListItemTitle.text = productX.title
            binding.txtAllCatListItemCount.text = productX.count+" کالای موجود"
        }
    }
}