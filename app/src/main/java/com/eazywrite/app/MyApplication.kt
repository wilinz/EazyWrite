package com.eazywrite.app

import android.app.Application
import org.litepal.LitePal

class MyApplication : Application() {

    companion object {
        lateinit var instance: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        LitePal.initialize(this)
    }
}