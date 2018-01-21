package com.rodvar.cwbprep

import android.os.Bundle

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        toolbarText = "Account Details"
        layoutResId = R.layout.activity_main
        super.onCreate(savedInstanceState)
    }
}
