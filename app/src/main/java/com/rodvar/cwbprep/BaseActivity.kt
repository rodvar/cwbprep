package com.rodvar.cwbprep

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.view.*

/**
 * Created by rodvar on 21/1/18.
 */
abstract class BaseActivity : AppCompatActivity() {

    protected var toolbarText = ""
    protected var layoutResId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(layoutResId)
        this.setupToolbar()
    }

    open fun setupToolbar() {
        this.setSupportActionBar(my_toolbar)
        my_toolbar.toolbar_text.text = this.toolbarText

    }
}