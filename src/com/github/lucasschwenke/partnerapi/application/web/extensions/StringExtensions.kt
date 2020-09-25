package com.github.lucasschwenke.partnerapi.application.web.extensions

fun String.onlyNumbersAndLetters(): String {
    val regex = Regex("[^A-Za-z0-9 ]")
    return regex.replace(this, "")
}
