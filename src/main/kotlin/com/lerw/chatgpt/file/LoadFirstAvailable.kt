package com.lerw.chatgpt.file

import java.io.File
import java.io.FileNotFoundException

fun readFromFirstExisting(vararg paths: String): String {
    for (path in paths) {
        val file = File(path)
        if (file.exists()) {
            return file.readText()
        }
    }
    throw FileNotFoundException("None of the following files exist: ${paths.joinToString(", ")}")
}
