package com.rodvar.cwbprep

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.view.*

/**
 * Created by rodvar on 21/1/18.
 */
abstract class BaseActivity : AppCompatActivity() {

    companion object {
        lateinit var currentActivity : Context
    }

    protected var toolbarText = ""
    protected var layoutResId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        currentActivity = this
        super.onCreate(savedInstanceState)
        this.setContentView(layoutResId)
        this.setupToolbar()
    }

    open fun setupToolbar() {
        this.setSupportActionBar(my_toolbar)
        my_toolbar.toolbar_text.text = this.toolbarText

    }

    abstract fun refresh()

    fun showProgress(visible: Boolean) {
        this.runOnUiThread({
            if (visible)
                this.progressBar?.visibility = View.VISIBLE
            else
                this.progressBar?.visibility = View.GONE
            Log.d(javaClass.simpleName, "progress visibility: " + visible)
        })

    }
}