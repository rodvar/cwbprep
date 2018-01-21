package com.rodvar.cwbprep

import com.rodvar.cwbprep.data.AccountDetailsRepository
import com.rodvar.cwbprep.domain.AccountDetails
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by rodvar on 21/1/18.
 */
class MainActivityPresenter {

    companion object {
        const val NEGATIVE_SIGN = "-"
        const val DOLLAR_SIGN = "$"
    }

    private lateinit var disposable: Disposable
    private var view: BaseActivity? = null
    private val transactionRepository : AccountDetailsRepository = AccountDetailsRepository()

    private var accountDetails: AccountDetails? = null

    fun onCreate() {
    }

    fun onViewCreated() {
        val all = transactionRepository.get()
        disposable = all.observeOn(AndroidSchedulers.mainThread())
                .doOnNext { showProgress() }
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    this.accountDetails = it
                    this.view?.refresh()
                }
    }

    fun onDestroy() {
        if (!disposable.isDisposed)
            disposable.dispose()
        this.view = null
    }

    private fun showProgress() {
        // TODO
    }

    fun bind(mainActivity: BaseActivity) {
        this.view = mainActivity
    }

    val accountName: String?
        get() = this.accountDetails?.account?.accountName
    val accountNumber: String?
        get() = this.accountDetails?.account?.accountNumber
    val accountBalance: String?
        get() = DOLLAR_SIGN + this.accountDetails?.account?.balance?.toString()
    val accountFunds: String?
        get() = DOLLAR_SIGN + this.accountDetails?.account?.available?.toString()
    val transactionsSize: Int?
        get() = this.accountDetails?.transactionsSize()

    fun onBindItem(holder: MainActivity.TransactionViewHolder?, position: Int) {
        val transactionsSortedByDescendingDate = this.accountDetails?.transactionsSortedByDescendingDate()
        holder?.description?.text = transactionsSortedByDescendingDate?.get(position)?.description
        val amount = transactionsSortedByDescendingDate?.get(position)?.amount
        if (amount != null) {
            if (amount.compareTo(0) >= 0)
                holder?.amount?.text = DOLLAR_SIGN + amount
            else
                holder?.amount?.text = NEGATIVE_SIGN + DOLLAR_SIGN + Math.abs(amount)
        }

    }

    fun hasTransactions(): Boolean = when (this.transactionsSize) {
        null -> false
        else -> this.transactionsSize!! > 0
    }


}