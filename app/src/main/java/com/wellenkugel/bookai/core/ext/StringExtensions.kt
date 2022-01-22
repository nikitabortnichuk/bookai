package com.wellenkugel.bookai.core.ext

fun String.enforceHttps(): String {
    return if (this.startsWith("https")) this
    else
        this.replaceFirst("http", "https")
}
