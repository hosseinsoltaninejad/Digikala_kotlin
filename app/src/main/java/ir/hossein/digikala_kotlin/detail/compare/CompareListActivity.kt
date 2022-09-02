package ir.hossein.digikala_kotlin.detail.compare

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.hossein.digikala_kotlin.R
import ir.hossein.digikala_kotlin.data.ComparableProduct
import ir.hossein.digikala_kotlin.detail.compare.adapter.CompareListAdapter
import ir.hossein.digikala_kotlin.utils.MyActivity
import org.clicksite.digikala_kotlin.utils.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CompareListActivity : MyActivity(), CompareListAdapter.OnCompareItemClickListener {
    private val comparableViewModel:ComparableViewModel by viewModel { parametersOf(intent.extras!!)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compare_list)

        val recyclerview=findViewById<RecyclerView>(R.id.rv_compareList)
        recyclerview.layoutManager= LinearLayoutManager(this)

        comparableViewModel.progressBarLiveData.observe(this){
            showProgressBar(it)
        }
        comparableViewModel.comparableListLiveData.observe(this){
            val adapter:CompareListAdapter by inject()
            adapter.comparebleProductList= it as ArrayList<ComparableProduct>
            adapter.setCompareListener(this)
            recyclerview.adapter=adapter
        }
    }

    override fun onItemClick(comparableProduct: ComparableProduct) {
        val intent= Intent(applicationContext,CompareActivity::class.java).apply {
            putExtra(PRODUCT_ID,comparableViewModel.firstProductId)
            putExtra(PRODUCT_TITLE,comparableViewModel.firstProductTitle)
            putExtra(PRODUCT_IMG,comparableViewModel.firstProductImage)
            putExtra(PRODUCT_PRICE,comparableViewModel.firstProductPrice)
            putExtra(SECOND_PRODUCT,comparableProduct.id)
        }
        startActivity(intent)
    }
}