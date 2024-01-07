package com.lerw.chatgpt

import java.util.*

fun localImageUrl(filePath: String): String {
    val imageBytes = java.io.File(filePath).readBytes()
    val imageBase64Bytes = Base64.getEncoder().encode(imageBytes)
    val imageBase64String = String(imageBase64Bytes)

    val imageExtension = filePath.split(".").last()
    return "data:image/$imageExtension;base64,$imageBase64String"
}