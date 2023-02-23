package com.eazywrite.app.ui.welcome

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eazywrite.app.R
import com.eazywrite.app.util.setWindow

class WelcomeActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindow()
        setContentView(R.layout.fragment_welcome)
    }
}