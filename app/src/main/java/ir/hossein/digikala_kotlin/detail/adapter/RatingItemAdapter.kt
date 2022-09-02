package ir.hossein.digikala_kotlin.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar
import ir.hossein.digikala_kotlin.R
import ir.hossein.digikala_kotlin.data.RatingItem
import ir.hossein.digikala_kotlin.databinding.RatingItemBinding

class RatingItemAdapter(private val ratingItemsList:List<RatingItem>): RecyclerView.Adapter<RatingItemAdapter.RatingItemViewHolder>() {

    lateinit var binding : RatingItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingItemViewHolder {
       binding = RatingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RatingItemViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RatingItemViewHolder, position: Int) {
        holder.bindViews(ratingItemsList[position])
    }

    override fun getItemCount(): Int= ratingItemsList.size

   inner class RatingItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindViews(ratingItem: RatingItem){
            binding.txtRatingItemTitle.text = ratingItem.title
            binding.roundCornerProgressBar.progress = ratingItem.value.toFloat()
        }
    }
}