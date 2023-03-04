package com.eazywrite.app.ui.image_editing

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.eazywrite.app.ui.theme.EazyWriteTheme
import com.eazywrite.app.util.setWindow

class ImagePreviewActivity : ComponentActivity() {

    companion object {

        const val KEY_IMAGES_URI = "KEY_IMAGES_URI"
        fun start(context: Context, uris: List<Uri>) {
            context.startActivity(Intent(context, ImagePreviewActivity::class.java).apply {
                putParcelableArrayListExtra(KEY_IMAGES_URI, ArrayList(uris))
            })
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindow()

//        val uris = intent?.getParcelableArrayListExtra(KEY_IMAGES_URI,)
        setContent {
            EazyWriteTheme {
                Surface(color = MaterialTheme.colorScheme.background) {

                }
            }
        }
    }

}