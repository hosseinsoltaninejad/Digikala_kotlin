package ir.hossein.digikala_kotlin.detail.compare

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import ir.hossein.digikala_kotlin.R
import ir.hossein.digikala_kotlin.costum.MyImageView
import ir.hossein.digikala_kotlin.data.Property
import ir.hossein.digikala_kotlin.home.ImageLoading
import ir.hossein.digikala_kotlin.utils.MyActivity
import ir.hossein.digikala_kotlin.detail.compare.adapter.CompareProductAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CompareActivity : MyActivity() {
    private val compareViewModel: CompareViewModel by viewModel { parametersOf(intent.extras) }
    val imageLoading: ImageLoading by inject()
    val adapter: CompareProductAdapter by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compare)

        val txtToolbar=findViewById<TextView>(R.id.txt_toolbar_title)
        txtToolbar.text="Ù…Ù‚Ø§ÛŒØ³Ù‡ Ù…Ø­ØµÙˆÙ„Ø§Øª"
        val recyclerView=findViewById<RecyclerView>(R.id.rv_compare)
        recyclerView.layoutManager= LinearLayoutManager(this)
        val dividerItemDecoration=
            DividerItemDecoration(recyclerView.context,DividerItemDecoration.VERTICAL).apply {
            ContextCompat.getDrawable(recyclerView.context,R.drawable.shape_divider)?.let {
                setDrawable(
                    it
                )
            }
        }
        recyclerView.addItemDecoration(dividerItemDecoration)

        val imgRemove=findViewById<ImageView>(R.id.img_compare_removeProduct)
        val frameSecondProduct=findViewById<LinearLayout>(R.id.linear_second_frame)
        val emptyStateFrame=findViewById<LinearLayout>(R.id.frame_compare_addProduct)
        val btnAddProduct=findViewById<MaterialButton>(R.id.btn_compare_addProduct)

        btnAddProduct.setOnClickListener{
            finish()
        }
        imgRemove.setOnClickListener{
            adapter.removeSecondProduct()
            frameSecondProduct.visibility= View.GONE
            emptyStateFrame.visibility=View.VISIBLE
        }
        val firstImg=findViewById<MyImageView>(R.id.img_compare_firstProduct)
        compareViewModel.firstImage?.let { imageLoading.load(firstImg, it) }
        val txtFirstTitle=findViewById<TextView>(R.id.txt_compare_firstProductTitle).apply {
            text=compareViewModel.firstTitle
        }
        val txtFirstPrice=findViewById<TextView>(R.id.txt_compare_firstProductPrice).apply {
            text=compareViewModel.firstPrice
        }

        val secondImg=findViewById<MyImageView>(R.id.img_compare_secondProduct)
        val txtSecondTitle=findViewById<TextView>(R.id.txt_compare_secondProductTitle)
        val txtSecondPrice=findViewById<TextView>(R.id.txt_compare_secondProductPrice)


        compareViewModel.secondDetailLiveData.observe(this){
            imageLoading.load(secondImg,it[0].image)
            txtSecondTitle.text=it[0].title
            txtSecondPrice.text=it[0].price
        }

        compareViewModel.firstProductPropertiesLiveData.observe(this) {
            adapter.firstPropertiesList= it as ArrayList<Property>?
            recyclerView.adapter=adapter
        }
        compareViewModel.secondProductPropertiesLiveData.observe(this) {
            adapter.secondPropertiesList=it as ArrayList<Property>
        }
        compareViewModel.progressBarLiveData.observe(this) {
            showProgressBar(it)
        }

        compareViewModel.firstTitle?.let {
            Log.i("LOG", it)
        }
    }
}