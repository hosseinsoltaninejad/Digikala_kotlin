package ir.hossein.digikala_kotlin.detail.chart

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import ir.hossein.digikala_kotlin.R
import ir.hossein.digikala_kotlin.databinding.ActivityChartBinding
import ir.hossein.digikala_kotlin.utils.MyActivity
import org.clicksite.digikala_kotlin.utils.DATA
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

private val dates= mutableListOf<String>()
class ChartActivity : MyActivity() {
    lateinit var binding: ActivityChartBinding
    private val chartViewModel: ChartViewModel by viewModel { parametersOf(intent.extras!!.getString(DATA)) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityChartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.layoutToolbar.txtToolbarTitle.text = "نمودار قیمت"

        chartViewModel.chartLiveData.observe(this){
            if(it.isNotEmpty()){
                var values:MutableList<Entry> = ArrayList()
                for(i in it.indices){
                    values.add(Entry(i.toFloat(),it[i].price.toFloat()))
                    dates.add(it[i].date)
                }
                val lineDataSet= LineDataSet(values,"نمودار قیمت محصول").apply {
                    color = ContextCompat.getColor(applicationContext,R.color.red)
                    lineWidth=3f
                    valueTextSize=10f
                    valueTextColor=ContextCompat.getColor(applicationContext,R.color.black)
                    setCircleColor(ContextCompat.getColor(applicationContext,R.color.yellow))
                    fillColor=ContextCompat.getColor(applicationContext,R.color.green)
                    setDrawFilled(true)
                    fillDrawable=ContextCompat.getDrawable(applicationContext,R.drawable.gradiant_chart)
                }
                val iLineDataSets:MutableList<ILineDataSet> = ArrayList()
                iLineDataSets.add(lineDataSet)

                val lineData= LineData(iLineDataSets)
                binding.chart.data=lineData
                val xAxis=binding.chart.xAxis
                xAxis.labelCount=3
                xAxis.valueFormatter=MyXAxisFormatter()
                binding.chart.animateX(500)
                binding.chart.invalidate()
            }
        }
    }
    class MyXAxisFormatter : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return dates.getOrNull(value.toInt()) ?: value.toString()
        }
    }
}