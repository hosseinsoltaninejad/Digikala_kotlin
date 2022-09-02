package ir.hossein.digikala_kotlin.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ir.hossein.digikala_kotlin.R
import org.clicksite.digikala_kotlin.utils.CHART
import org.clicksite.digikala_kotlin.utils.COMPARE
import org.clicksite.digikala_kotlin.utils.SHARE


class MoreBottomSheetDialog : BottomSheetDialogFragment() {
    var myView:View?=null
    private lateinit var onMoreDialogItemClickListener: OnMoreDialogItemClickListener
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(myView ==null){
            myView=inflater.inflate(R.layout.more_dialog, null)
        }
        return myView
    }

    fun setItemClickListener(onMoreDialogItemClickListener: OnMoreDialogItemClickListener){
        this.onMoreDialogItemClickListener=onMoreDialogItemClickListener
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val txtShare=view.findViewById<TextView>(R.id.txt_moreDialog_share)
        val txtCompare=view.findViewById<TextView>(R.id.txt_moreDialog_compare)
        val txtChart=view.findViewById<TextView>(R.id.txt_moreDialog_chart)

        txtShare.setOnClickListener{
            onMoreDialogItemClickListener.onItemClickListener(SHARE)
            dismiss()
        }
        txtChart.setOnClickListener{
            onMoreDialogItemClickListener.onItemClickListener(CHART)
            dismiss()
        }
        txtCompare.setOnClickListener{
            onMoreDialogItemClickListener.onItemClickListener(COMPARE)
            dismiss()
        }

    }

    interface OnMoreDialogItemClickListener{
        fun onItemClickListener(itemType:String)
    }
}