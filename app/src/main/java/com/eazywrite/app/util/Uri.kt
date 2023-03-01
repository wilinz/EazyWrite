package com.eazywrite.app.util

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.database.getStringOrNull
import java.io.File

fun Uri.copyToFile(context: Context, file: File) {
    context.contentResolver.openInputStream(this).use { input ->
        file.outputStream().use { output ->
            input?.copyTo(output)
        }
    }
}

fun Uri.copyToCacheFile(context: Context): File {
    val file = File(context.cacheDir,"/image/${System.currentTimeMillis()}")
    file.createFile()
    copyToFile(context, file)
    return file
}

fun Uri.getFileName(context: Context): String? {
    var result: String? = null
    if (this.scheme == "content") {
        val cursor = context.contentResolver.query(this, null, null, null, null)
        cursor.use { cursor ->
            if (cursor != null && cursor.moveToFirst()) {
                result = cursor.getStringOrNull(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        }
    }
    if (result == null) {
        result = this.path
        val cut = result?.lastIndexOf('/') ?: -1
        if (cut != -1) {
            result = result?.substring(cut + 1)
        }
    }
    return result
}