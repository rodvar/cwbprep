package com.rodvar.cwbprep

import android.app.Application
import android.content.Context

/**
 * Created by rodvar on 21/1/18.
 */
class CwbPrepApplication : Application() {

    companion object {
        lateinit var context : Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}
