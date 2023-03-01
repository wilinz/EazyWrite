package com.eazywrite.app.util

import android.util.Base64
import java.io.File


fun base64ToFile(file: File, base64: String?): File {
    file.createFile()
    val bytes = Base64.decode(base64, Base64.DEFAULT)
    file.writeBytes(bytes)
    return file
}
