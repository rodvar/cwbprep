package com.rodvar.cwbprep

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import android.widget.TextView
import com.rodvar.cwbprep.data.AccountDetailsRepository
import com.rodvar.cwbprep.domain.AccountDetails
import com.rodvar.cwbprep.domain.Transaction
import com.rodvar.cwbprep.util.DateUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*


/**
 * Created by rodvar on 21/1/18.
 */
class MainActivityPresenter {

    companion object {
        const val PENDING_PREFIX = "PENDING: "
        const val NEGATIVE_SIGN = "-"
        const val DOLLAR_SIGN = "$"
    }

    private lateinit var disposable: Disposable
    private var view: BaseActivity? = null
    private val transactionRepository : AccountDetailsRepository = AccountDetailsRepository()

    private var accountDetails: AccountDetails? = null

    fun onViewCreated() {
        val all = transactionRepository.get()
        this.view?.showProgress(true)
        disposable = all
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError({
                    this.view?.showProgress(false)
                    Log.e(javaClass.simpleName, "error fetching data", it)
                })
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

    fun onBindItem(holder: MainActivity.TransactionViewHolder, position: Int) {
        val transactionsSortedByDescendingDate = this.accountDetails?.transactionsSortedByDescendingDate()
        val transaction = transactionsSortedByDescendingDate?.get(position)

        holder.description.text = transaction?.description
        if (transaction?.isPending()!!)
            boldPending(holder.description)

        if (transaction.isWithdrawal())
            holder.localizable.visibility = View.VISIBLE

        val amount = transaction.amount
        if (amount.compareTo(0) >= 0)
            holder.amount.text = DOLLAR_SIGN + amount
        else
            holder.amount.text = NEGATIVE_SIGN + DOLLAR_SIGN + Math.abs(amount)

        if (transaction.effectiveDate != null ) {
            holder.date.text = DateUtil.formatDate(transaction.effectiveDate!!)

            if (position == 0 || dateChanged(transactionsSortedByDescendingDate, position, transaction))
                holder.header.visibility = View.VISIBLE
            else
                holder.header.visibility = View.GONE
        }

    }

    private fun dateChanged(transactionsSortedByDescendingDate: List<Transaction>?, position: Int, transaction: Transaction) : Boolean {
        return transactionsSortedByDescendingDate?.get(position - 1)?.effectiveDate?.compareTo(transaction.effectiveDate!!) != 0
    }

    private fun boldPending(description: TextView) {
        val theRest = description.text.toString()
        val sb = SpannableStringBuilder(PENDING_PREFIX + theRest)
        sb.setSpan(android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, PENDING_PREFIX.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        description.text = sb
    }

    fun hasTransactions(): Boolean = when (this.transactionsSize) {
        null -> false
        else -> this.transactionsSize!! > 0
    }


}