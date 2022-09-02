package ir.hossein.digikala_kotlin.utils

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ir.hossein.digikala_kotlin.R
import java.text.DecimalFormat

abstract class BaseViewModel : ViewModel(){
     val  compositeDisposable = CompositeDisposable()
    val progressBarLiveData = MutableLiveData<Boolean>()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}

abstract  class MySingleObserver<T> (private val  compositeDisposable: CompositeDisposable) : SingleObserver<T> {

    override fun onSubscribe(d: Disposable) {
       compositeDisposable.add(d)
    }

    override fun onError(e: Throwable) {
        Log.i(TAG,  e.toString())
    }
}

fun <T> Single<T>.singleHelper() : Single<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

interface MyView {
    val root : CoordinatorLayout?
    val  myContext : Context?
    fun showProgressBar (show : Boolean){
        root?.let { rootView ->
            myContext?.let { cntx->
                var progressBarView = rootView.findViewById<View>(R.id.frameProgressBar)
                if (progressBarView == null && show){
                    progressBarView = LayoutInflater.from(cntx).inflate(R.layout.progressbar, rootView, false)
                    rootView.addView(progressBarView)
                }
                progressBarView.visibility = if (show)View.VISIBLE else View.GONE
            }
        }
    }

    fun showEmptyState(layout:Int):View?{
        root?.let {
            myContext?.let { context ->
                var emptyState=it.findViewById<View>(R.id.root_emptyState)
                if(emptyState==null){
                    emptyState=LayoutInflater.from(context).inflate(layout,it,false)
                    it.addView(emptyState)
                }
                emptyState.visibility=View.VISIBLE
                return emptyState
            }
        }

        return null
    }
}

abstract class MyActivity : AppCompatActivity() , MyView{
    override val root: CoordinatorLayout?
        get() {
            val parent = window.decorView.findViewById<ViewGroup>(android.R.id.content)
            if (parent !is CoordinatorLayout) {
                parent.children.forEach {
                    if (it is CoordinatorLayout) {
                        return it
                    }
                }
                throw Exception("root must be Coordinator Layout")
            } else {
                return parent
            }
        }

    override val myContext: Context?
        get() = this
}

abstract class MyFragment : Fragment() , MyView {
    override val root: CoordinatorLayout?
        get() = view as CoordinatorLayout

    override val myContext: Context?
        get() = context
}


fun currencyFormat(amount: String): String {
    val formatter = DecimalFormat("###,###,###")
    return formatter.format(amount.toDouble())
}


fun setPersianNumbers(str: String): String {
    return str
        .replace("0", "۰")
        .replace("1", "۱")
        .replace("2", "۲")
        .replace("3", "۳")
        .replace("4", "۴")
        .replace("5", "۵")
        .replace("6", "۶")
        .replace("7", "۷")
        .replace("8", "۸")
        .replace("9", "۹")
}