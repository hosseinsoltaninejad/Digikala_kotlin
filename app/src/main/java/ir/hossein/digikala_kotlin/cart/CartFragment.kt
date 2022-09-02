package ir.hossein.digikala_kotlin.cart

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ir.hossein.digikala_kotlin.R
import ir.hossein.digikala_kotlin.data.CartItem
import ir.hossein.digikala_kotlin.login.LoginActivity
import ir.hossein.digikala_kotlin.utils.MyFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CartFragment : MyFragment(),CartItemAdapter.OnCartClickListener {
    private val cartViewModel:CartViewModel by viewModel()
    val compositeDisposable=CompositeDisposable()
    var cartItemAdapter:CartItemAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        cartViewModel.getCartList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerview=view.findViewById<RecyclerView>(R.id.recyclerView_cart)
        recyclerview.layoutManager=LinearLayoutManager(context)
        val btnPay=view.findViewById<Button>(R.id.btn_cart_pay)

        btnPay.setOnClickListener{
            val intent=Intent(Intent.ACTION_VIEW, Uri.parse("http://clicksite.org/app_digi_mellat/example.php"))
            startActivity(intent)
        }

        cartViewModel.cartResponseLiveData.observe(viewLifecycleOwner){

            val cartAdapter:CartItemAdapter by inject{ parametersOf(it)}
            cartItemAdapter=cartAdapter
            recyclerview.adapter=cartAdapter
            cartAdapter.onCartClickListener=this@CartFragment
            Log.i("LOG", it.toString())
        }
        cartViewModel.progressBarLiveData.observe(viewLifecycleOwner){
            showProgressBar(it)
        }

        cartViewModel.totalPriceLiveData.observe(viewLifecycleOwner){
            cartItemAdapter?.let {cartItemAdapter->
                cartItemAdapter.totalPrice=it
                cartItemAdapter.notifyDataSetChanged()
            }
        }

        cartViewModel.emptyStateLiveData.observe(viewLifecycleOwner){
            val parent=view.findViewById<ConstraintLayout>(R.id.root_emptyState)
            if(it.show){
                val emptyState=showEmptyState(R.layout.empty_state)
                emptyState?.let {view->

                    val txtMessage=view.findViewById<TextView>(R.id.txt_emptyState)
                    val btnEmptyState=view.findViewById<Button>(R.id.btn_emptyState)

                    txtMessage.text=getString(it.message)

                    if(it.showButton){
                        btnEmptyState.visibility=View.VISIBLE
                    }else{
                        btnEmptyState.visibility=View.GONE
                    }

                    btnEmptyState.setOnClickListener{
                        startActivity(Intent(context, LoginActivity::class.java))
                    }

                }
            }else{
                parent?.let {
                    it.visibility=View.GONE
                }
            }
        }
    }

    override fun onIncreaseClick(cartItem: CartItem, count: Int) {
        cartItemAdapter!!.increaseCartItemCount(cartItem,count)
        val newCount=count+1
        cartViewModel.changeCartItemCount(cartItem,newCount,true).
        subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onComplete() {

                }

                override fun onError(e: Throwable) {
                    Log.i("LOG", "onError: ${e.toString()}")
                }

            })
    }

    override fun onDecreaseClick(cartItem: CartItem, count: Int) {
        cartItemAdapter!!.decreaseItemCount(cartItem,count)
        val newCount=count-1
        cartViewModel.changeCartItemCount(cartItem,newCount,false).
        subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onComplete() {

                }

                override fun onError(e: Throwable) {
                    Log.i("LOG", "onError: ${e.toString()}")
                }

            })
    }

    override fun onItemClick(cartItem: CartItem) {
        TODO("Not yet implemented")
    }



    override fun onDeleteItem(cartItem: CartItem) {
        cartViewModel.removeCartItem(cartItem)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }
                override fun onComplete() {

                }
                override fun onError(e: Throwable) {
                    Log.i("LOG", "onError: ${e.toString()}")
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}