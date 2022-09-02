package ir.hossein.digikala_kotlin.detail

import android.content.Intent
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.disposables.CompositeDisposable
import ir.hossein.digikala_kotlin.R
import ir.hossein.digikala_kotlin.data.LoginMessage
import ir.hossein.digikala_kotlin.data.RatingItem
import ir.hossein.digikala_kotlin.databinding.ActivityDetailBinding
import ir.hossein.digikala_kotlin.detail.adapter.RatingItemAdapter
import ir.hossein.digikala_kotlin.detail.chart.ChartActivity
import ir.hossein.digikala_kotlin.detail.compare.CompareListActivity
import ir.hossein.digikala_kotlin.detail.viewModel.DetailViewModel
import ir.hossein.digikala_kotlin.home.ImageLoading
import ir.hossein.digikala_kotlin.login.LoginActivity
import ir.hossein.digikala_kotlin.login.LoginViewModel
import ir.hossein.digikala_kotlin.utils.MyActivity
import ir.hossein.digikala_kotlin.utils.MySingleObserver
import ir.hossein.digikala_kotlin.utils.singleHelper
import org.clicksite.digikala_kotlin.utils.*
import org.json.JSONArray
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailActivity : MyActivity() , MoreBottomSheetDialog.OnMoreDialogItemClickListener {
    private lateinit var binding: ActivityDetailBinding
    private val imageLoading: ImageLoading by inject()
    private val detailViewModel: DetailViewModel by viewModel { parametersOf(intent.extras!!.getString("id")) }
    private val loginViewModel: LoginViewModel by viewModel()
    val compositeDisposable= CompositeDisposable()
    var cat: String? = null
    var productId: String? = null
    var productTitle: String? = null
    var productImg: String? = null
    var productPrice: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewDetailRating.layoutManager = LinearLayoutManager(this)

        detailViewModel.detailLiveData.observe(this){
            productId = it[0].id
            productImg=it[0].image
            productTitle=it[0].title
            productPrice=it[0].price
            cat = it[0].cat
            imageLoading.load(binding.imgDetailImage, it[0].image)
            binding.txtDetailTitle.text = it[0].title
            binding.txtDetailPrice.text = it[0].price
            binding.txtDetailPrePrice.text = it[0].pprice
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.txtDetailIntroduction.text = Html.fromHtml(it[0].introduction, Html.FROM_HTML_MODE_COMPACT)
            } else {
                binding.txtDetailIntroduction.text = Html.fromHtml(it[0].introduction)
            }
            binding.txtDetailPrePrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

            binding.txtDetailColor.text = it[0].colors
            binding. txtDetailGaurantee.text = it[0].garantee
            binding.ratingDetail.rating = it[0].rating.toFloat()
            val ratingItems = it[0].rating_item

            val ratingItemsList = ArrayList<RatingItem>()

            val ratingArray = JSONArray(ratingItems)
            for (i in 0 until ratingArray.length()) {
                val jsonObject = ratingArray.getJSONObject(i)
                var ratingItem = RatingItem(jsonObject.getString("title"), jsonObject.getString("value"))
                ratingItemsList.add(ratingItem)
            }
            val ratingItemAdapter: RatingItemAdapter by inject { parametersOf (ratingItemsList) }
            binding.recyclerViewDetailRating.adapter = ratingItemAdapter
        }

        detailViewModel.progressBarLiveData.observe(this){
            showProgressBar(it)
        }

        binding.imgDetailProperties.setOnClickListener {
            startActivity(Intent(applicationContext, PropertyActivity::class.java))
        }

        binding.imgDetailClose.setOnClickListener {
            finish()
        }

        binding.imgDetailFav.setOnClickListener {
            if(loginViewModel.checkLoginStatus.value==false){
                startActivity(Intent(applicationContext, LoginActivity::class.java))
                finish()
            }else{
                if(binding.imgDetailFav.contentDescription=="white"){
                    loginViewModel.addToFav(productId!!)
                }else{
                    loginViewModel.deleteFav(productId!!)
                }
            }
        }

        loginViewModel.favoriteLiveData.observe(this){
            if(it.status=="error"){
                Toast.makeText(applicationContext,"خطای ناشناخته",Toast.LENGTH_SHORT).show()
            }else{
                if(it.status=="add"){
                    binding.imgDetailFav.setImageResource(R.drawable.ic_favorite_red)
                    binding.imgDetailFav.contentDescription="red"
                    Toast.makeText(applicationContext,"محصول مورد نظر با موفقیت به علاقه مندی ها اضافه شد",Toast.LENGTH_SHORT).show()
                }else{
                    binding.imgDetailFav.setImageResource(R.drawable.ic_favorite_border)
                    binding.imgDetailFav.contentDescription="white"
                    Toast.makeText(applicationContext,"محصول مورد نظر با موفقیت از علاقه مندی ها حذف شد",Toast.LENGTH_SHORT).show()
                }
            }
        }

        detailViewModel.detailFavLiveData.observe(this){
            if(it.isNullOrEmpty()){
                binding.imgDetailFav.setImageResource(R.drawable.ic_favorite_border)
                binding.imgDetailFav.contentDescription="white"
            }else{
                it.forEach {
                    if(productId==it.fav_idproduct){
                        binding.imgDetailFav.setImageResource(R.drawable.ic_favorite_red)
                        binding.imgDetailFav.contentDescription="red"
                    }
                }

            }
        }

        binding.imgDetailMore.setOnClickListener {
            val moreBottomSheetDialog = MoreBottomSheetDialog()
            moreBottomSheetDialog.show(supportFragmentManager, null)
            moreBottomSheetDialog.setItemClickListener(this)
            /*val popupMenu=PopupMenu(applicationContext,imgMore).apply {
               menuInflater.inflate(R.menu.popup_menu,menu)
            }
            popupMenu.setOnMenuItemClickListener {
                when (it.title) {
                    "Ø§Ø´ØªØ±Ø§Ú© Ú¯Ø°Ø§Ø±ÛŒ Ù…Ø­ØµÙˆÙ„" -> {
                        Toast.makeText(applicationContext,"share",Toast.LENGTH_SHORT).show()
                    }
                    "Ù…Ù‚Ø§ÛŒØ³Ù‡ Ù…Ø­ØµÙˆÙ„" -> {
                        Toast.makeText(applicationContext,"compare",Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(applicationContext,"chart",Toast.LENGTH_SHORT).show()
                    }
                }
                return@setOnMenuItemClickListener true
            }

            popupMenu.show()*/
        }

        binding.btnDetailAddToBasket.setOnClickListener{
            Log.i("TAG", "onCreate: "+productId)
            detailViewModel.addToBasket(productId!!)
                .singleHelper()
                .subscribe(object : MySingleObserver<LoginMessage>(compositeDisposable){
                    override fun onSuccess(t: LoginMessage) {
                        Log.i("TAG", "onCreate: "+t)
                        if(t.status=="success"){
                            Toast.makeText(this@DetailActivity,"محصول با موفقیت به سبد خرید اضافه شد",Toast.LENGTH_SHORT).show()
                        }
                    }
                })
        }
    }

    override fun onItemClickListener(itemType: String) {
        when (itemType) {
            SHARE -> {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "https://www.digikala.com/product/dkp-3513389/%D9%87%D9%86%D8%AF%D8%B2%D9%81%D8%B1%DB%8C-%D8%A8%D9%84%D9%88%D8%AA%D9%88%D8%AB%DB%8C-%D9%86%DB%8C%D9%BE%D9%88-%D9%85%D8%AF%D9%84np-40"
                    )
                }
                startActivity(Intent.createChooser(intent, "Ù…Ø¹Ø±ÙÛŒ Ù…Ø­ØµÙˆÙ„"))
            }
            CHART -> {
                val intent = Intent(applicationContext, ChartActivity::class.java).apply {
                    putExtra(DATA, productId)
                }
                startActivity(intent)
            }
            COMPARE -> {
               val intent = Intent(applicationContext, CompareListActivity::class.java).apply {
                    putExtra(DATA, cat)
                    putExtra(PRODUCT_ID, productId)
                    putExtra(PRODUCT_IMG, productImg)
                    putExtra(PRODUCT_TITLE, productTitle)
                    putExtra(PRODUCT_PRICE, productPrice)
                }
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loginViewModel.checkLogin()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}