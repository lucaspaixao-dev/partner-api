package com.github.lucasschwenke.partnerapi.application.web.validations

data class Validation<T>(
    val fieldName: String,
    val fieldValue: T,
    val errorMessageList: MutableList<String> = mutableListOf()
)
