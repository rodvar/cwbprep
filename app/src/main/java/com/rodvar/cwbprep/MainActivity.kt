package com.rodvar.cwbprep

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.account_details.*
import kotlinx.android.synthetic.main.account_details.view.*

class MainActivity : BaseActivity() {

    private var transactionListView: RecyclerView? = null
    private var transactionListAdapter: RecyclerView.Adapter<*>? = null
    private var transactionListLayoutManager: RecyclerView.LayoutManager? = null

    private lateinit var mainActivityPresenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        toolbarText = "Account Details"
        layoutResId = R.layout.activity_main
        this.mainActivityPresenter = MainActivityPresenter() // should use DI FW like dagger2
        this.mainActivityPresenter.bind(this)

        super.onCreate(savedInstanceState)

        this.transactionListLayoutManager = LinearLayoutManager(this)
        this.transactionListView?.layoutManager = this.transactionListLayoutManager
        this.mainActivityPresenter.onCreate()
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
    }


}
