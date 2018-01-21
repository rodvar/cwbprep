package com.rodvar.cwbprep

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.account_details.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.transaction.view.*

class MainActivity : BaseActivity() {

    private var transactionListAdapter: RecyclerView.Adapter<*>? = null
    private var transactionListLayoutManager: RecyclerView.LayoutManager? = null

    private lateinit var mainActivityPresenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        toolbarText = "Account Details"
        layoutResId = R.layout.activity_main
        this.mainActivityPresenter = MainActivityPresenter() // should use DI FW like dagger2
        this.mainActivityPresenter.bind(this)

        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        this.transactionListLayoutManager = LinearLayoutManager(this)
        this.transactionListAdapter = TransactionListAdapter(this.mainActivityPresenter)
        this.transactionListView.layoutManager = this.transactionListLayoutManager
        this.transactionListView.adapter = this.transactionListAdapter
        this.mainActivityPresenter.onViewCreated()
    }

    override fun onDestroy() {
        this.mainActivityPresenter.onDestroy()
        super.onDestroy()
    }

    override fun refresh() {
        this.account_name.text = this.mainActivityPresenter.accountName
        this.account_number.text = this.mainActivityPresenter.accountNumber
        this.avail_balance.text = this.mainActivityPresenter.accountBalance
        this.avail_funds.text = this.mainActivityPresenter.accountFunds

        if (this.mainActivityPresenter.hasTransactions()) {
            this.transactionListAdapter?.notifyDataSetChanged()
        } else
            this.showEmptyTransactionsView()

    }

    private fun showEmptyTransactionsView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var description: TextView? = view.description
    }

    private inner class TransactionListAdapter (val presenter: MainActivityPresenter) :
            RecyclerView.Adapter<TransactionViewHolder>() {
        override fun onBindViewHolder(holder: TransactionViewHolder?, position: Int) {
            this.presenter.onBindItem(holder, position)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.transaction, null, false)
            return TransactionViewHolder(view)
        }

        override fun getItemCount(): Int {
            return when (this.presenter.transactionsSize) {
                null -> 0
                else -> this.presenter.transactionsSize!!
            }
        }
    }


}
