package com.github.lucasschwenke.partnerapi.domain.exceptions

import java.lang.Exception

abstract class ApiException(
    message: String
) : Exception(message) {

    abstract fun httpStatus(): Int
    abstract fun apiError(): ApiError
    abstract fun userResponseMessage(): String
    abstract fun details(): Map<String, List<Any>>

    fun createErrorResponse() =
        ErrorResponse(
            apiError = apiError(),
            message = userResponseMessage(),
            details = details()
        )
}
