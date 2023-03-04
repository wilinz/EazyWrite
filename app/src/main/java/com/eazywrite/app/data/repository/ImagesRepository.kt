package com.eazywrite.app.data.repository

import android.net.Uri
import kotlinx.coroutines.flow.MutableStateFlow

object ImagesRepository {

    val images = MutableStateFlow<List<Uri>>(listOf())

}