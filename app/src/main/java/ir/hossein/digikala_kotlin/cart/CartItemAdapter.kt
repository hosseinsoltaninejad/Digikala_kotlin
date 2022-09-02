package ir.hossein.digikala_kotlin.cart

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ir.hossein.digikala_kotlin.R
import ir.hossein.digikala_kotlin.costum.MyImageView
import ir.hossein.digikala_kotlin.data.CartItem
import ir.hossein.digikala_kotlin.data.CartResponse
import ir.hossein.digikala_kotlin.home.ImageLoading
import ir.hossein.digikala_kotlin.utils.currencyFormat
import ir.hossein.digikala_kotlin.utils.setPersianNumbers
import java.util.*

const val CARTITEM = 0
const val PURCHASEITEM = 1

class CartItemAdapter(private val cartResponse: CartResponse, private val imageLoading: ImageLoading) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onCartClickListener: OnCartClickListener?=null
    var totalPrice:Int?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == CARTITEM) {
            CartItemViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
            )
        } else {
            PurchaseViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.purchase_item, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == CARTITEM) {
            val cartItem=cartResponse.message[position]
            (holder as CartItemViewHolder).txtTitle.text = cartResponse.message[position].title
            val productPrice=cartResponse.message[position].price.toInt()*cartResponse.message[position].count.toInt()
            holder.txtPrice.text = setPersianNumbers(currencyFormat(productPrice.toString()))
            holder.txtCount.text = cartResponse.message[position].count
            imageLoading.load(holder.image, cartResponse.message[position].image)

            if(cartItem.progressBarLoading){
                holder.progressBar.visibility=View.VISIBLE
                holder.txtCount.visibility=View.INVISIBLE
            }else{
                holder.progressBar.visibility=View.INVISIBLE
                holder.txtCount.visibility=View.VISIBLE
            }

            holder.imgDecreaseCount.setOnClickListener{
                if(cartItem.count.toInt()>1){
                    cartItem.progressBarLoading=true
                    holder.progressBar.visibility=View.VISIBLE
                    holder.txtCount.visibility=View.INVISIBLE
                    onCartClickListener!!.onDecreaseClick(cartItem,cartItem.count.toInt())
                }
            }

            holder.imgIncreaseCount.setOnClickListener{
                cartItem.progressBarLoading=true
                holder.progressBar.visibility=View.VISIBLE
                holder.txtCount.visibility=View.INVISIBLE
                onCartClickListener!!.onIncreaseClick(cartItem,cartItem.count.toInt())
            }

            holder.txtDelete.setOnClickListener{
                onCartClickListener!!.onDeleteItem(cartItem)
            }

        } else {
            Log.i("LOG", "onBindViewHolder: $totalPrice")
            (holder as PurchaseViewHolder).txtPrice.text = setPersianNumbers(currencyFormat(totalPrice.toString()))
            (holder as PurchaseViewHolder).txtTotlePrice.text = setPersianNumbers(currencyFormat(totalPrice.toString()))
            (holder as PurchaseViewHolder).txtDiscount.text= "${setPersianNumbers("0") } تومان "
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == cartResponse.message.size) {
            PURCHASEITEM
        } else {
            CARTITEM
        }
    }

    fun removeCartItem(cartItem: CartItem){
        val cartList:MutableList<CartItem> = cartResponse.message as MutableList<CartItem>
        val index=cartList.indexOf(cartItem)
        cartList.removeAt(index)
        cartResponse.message=cartList
        notifyItemRemoved(index)
    }

    fun increaseCartItemCount(cartItem: CartItem,count:Int){
        val cartList:MutableList<CartItem> = cartResponse.message as MutableList<CartItem>
        val index=cartList.indexOf(cartItem)
        val newCount=count+1
        cartItem.count=newCount.toString()
        cartItem.progressBarLoading=false
        cartList.set(index,cartItem)
        cartResponse.message=cartList
        notifyItemChanged(index)
    }
    fun decreaseItemCount(cartItem: CartItem,count:Int){
        val cartList:MutableList<CartItem> = cartResponse.message as MutableList<CartItem>
        val index=cartList.indexOf(cartItem)
        val newCount=count-1
        cartItem.count=newCount.toString()
        cartItem.progressBarLoading=false
        cartList.set(index,cartItem)
        cartResponse.message=cartList
        notifyItemChanged(index)
    }

    override fun getItemCount(): Int = (cartResponse.message.size) + 1

    class CartItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<MyImageView>(R.id.img_cartItem)
        val txtTitle = itemView.findViewById<TextView>(R.id.txt_cartItem_title)
        val txtPrice = itemView.findViewById<TextView>(R.id.txt_cartItem_price)
        val txtCount = itemView.findViewById<TextView>(R.id.txt_cartItem_count)
        val imgIncreaseCount=itemView.findViewById<ImageView>(R.id.img_cartItem_increase)
        val imgDecreaseCount=itemView.findViewById<ImageView>(R.id.img_cartItem_decrease)
        val txtDelete=itemView.findViewById<TextView>(R.id.txt_cartItem_delete)
        val progressBar=itemView.findViewById<ProgressBar>(R.id.progressBar_cartItem)
    }

    class PurchaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtPrice = itemView.findViewById<TextView>(R.id.txt_purchaseItem_price)
        val txtTotlePrice = itemView.findViewById<TextView>(R.id.txt_purchaseItem_totalPrice)
        val txtDiscount=itemView.findViewById<TextView>(R.id.txt_cartItem_discount)
    }

    interface OnCartClickListener{
        fun onIncreaseClick(cartItem: CartItem,count:Int)
        fun onDecreaseClick(cartItem: CartItem,count:Int)
        fun onItemClick(cartItem: CartItem)
        fun onDeleteItem(cartItem: CartItem)
    }
}