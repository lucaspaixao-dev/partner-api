package com.github.lucasschwenke.partnerapi.application.web.extensions

import br.com.caelum.stella.validation.CNPJValidator
import br.com.caelum.stella.validation.InvalidStateException
import com.github.lucasschwenke.partnerapi.application.web.validations.Validation

fun Validation<String>.isNullOrBlank(): Validation<String> {
    this.fieldValue.takeIf { it.isBlank() }?.run {
        errorMessageList.add("must not be empty or null.")
    }

    return this
}

fun <T> Validation<T>.isNull(): Validation<T> {
    if (this.fieldValue == null) {
        errorMessageList.add("must not be null.")
    }

    return this
}

fun Validation<String>.isInvalidCNPJ(): Validation<String> {
    try {
        val validator = CNPJValidator(false)
        validator.assertValid(this.fieldValue)
    } catch (e: InvalidStateException) {
        errorMessageList.add("the CNPJ is invalid.")
    }

    return this
}
