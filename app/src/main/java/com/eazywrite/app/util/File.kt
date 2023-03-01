package com.eazywrite.app.util

import java.io.File

fun createFile(path: String): File {
    val file = File(path)
    file.parentFile?.let {
        if (!it.exists()){
            it.mkdirs()
        }
    }
    file.createNewFile()
    return file
}

fun File.createFile(): File {
    val file = File(path)
    file.parentFile?.let {
        if (!it.exists()){
            it.mkdirs()
        }
    }
    file.createNewFile()
    return file
}

fun getFileExtension(string: String): String {
    return File(string).extension
}