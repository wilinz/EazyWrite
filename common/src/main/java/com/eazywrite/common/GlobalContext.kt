package com.eazywrite.common

import android.app.Application

object GlobalContext {
    @JvmStatic
    lateinit var application: Application

    fun init(application: Application) {
        this.application = application
    }
}