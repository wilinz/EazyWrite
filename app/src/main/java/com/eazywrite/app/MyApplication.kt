package com.eazywrite.app

import android.app.Application
import com.eazywrite.common.GlobalContext

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        GlobalContext.init(this)
    }
}