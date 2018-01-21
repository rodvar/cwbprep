package com.rodvar.cwbprep

import com.rodvar.cwbprep.data.AccountDetailsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by rodvar on 21/1/18.
 */
class MainActivityPresenter {
    val transactionRepository : AccountDetailsRepository = AccountDetailsRepository()
    fun onCreate() {
        val all = transactionRepository.get()
        all.observeOn(AndroidSchedulers.mainThread())
                .doOnNext { showProgress() }
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    System.out.println(it)
                }
    }

    private fun showProgress() {
        // TODO
    }


}