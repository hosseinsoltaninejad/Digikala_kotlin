package ir.hossein.digikala_kotlin.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ir.hossein.digikala_kotlin.R
import ir.hossein.digikala_kotlin.data.Cat
import ir.hossein.digikala_kotlin.databinding.CatItemBinding
import ir.hossein.digikala_kotlin.home.ImageLoading

class CatAdapter(private val catItems : List<Cat> , private val imageLoading: ImageLoading) : RecyclerView.Adapter<CatAdapter.CatViewHolder>() {
    private lateinit var binding : CatItemBinding
    inner class CatViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

       fun bindViews(cat: Cat){
           imageLoading.load(binding.imgCatItemImage, cat.position)
           binding.txtCatItemTitle.text = cat.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        binding = CatItemBinding.inflate(LayoutInflater.from(parent.context,), parent, false)
        return CatViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bindViews(catItems[position])
    }

    override fun getItemCount(): Int = catItems.size
}