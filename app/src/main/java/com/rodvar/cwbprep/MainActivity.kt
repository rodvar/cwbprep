package com.rodvar.cwbprep

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class MainActivity : BaseActivity() {

    private var transactionListView: RecyclerView? = null
    private var transactionListAdapter: RecyclerView.Adapter<*>? = null
    private var transactionListLayoutManager: RecyclerView.LayoutManager? = null

    private lateinit var mainActivityPresenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        toolbarText = "Account Details"
        layoutResId = R.layout.activity_main
        this.mainActivityPresenter = MainActivityPresenter() // should use DI FW like dagger2

        super.onCreate(savedInstanceState)

        this.transactionListLayoutManager = LinearLayoutManager(this)
        this.transactionListView?.layoutManager = this.transactionListLayoutManager
        this.mainActivityPresenter.onCreate()
    }


}
