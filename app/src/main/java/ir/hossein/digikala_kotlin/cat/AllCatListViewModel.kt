package ir.hossein.digikala_kotlin.cat

import androidx.lifecycle.MutableLiveData
import ir.hossein.digikala_kotlin.cat.repository.AllCatListReposiroy
import ir.hossein.digikala_kotlin.data.AllCatListItem
import ir.hossein.digikala_kotlin.utils.BaseViewModel
import ir.hossein.digikala_kotlin.utils.MySingleObserver
import ir.hossein.digikala_kotlin.utils.singleHelper

class AllCatListViewModel(allCatListReposiroy: AllCatListReposiroy) : BaseViewModel() {
    val allCatListItemLiveData = MutableLiveData<List<AllCatListItem>>()

    init {
        progressBarLiveData.value = true
        allCatListReposiroy.getAllCatList()
            .singleHelper()
            .doFinally {
                progressBarLiveData.value = false
            }
            .subscribe(object : MySingleObserver<List<AllCatListItem>>(compositeDisposable) {
                override fun onSuccess(t: List<AllCatListItem>) {
                    allCatListItemLiveData.value = t
                }
            })
    }
}