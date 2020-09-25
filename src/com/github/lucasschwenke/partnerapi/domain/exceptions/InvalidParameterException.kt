package com.github.lucasschwenke.partnerapi.domain.exceptions

class InvalidParameterException(message: String) : ApiException(message = message) {

    override fun httpStatus() = HttpStatus.BAD_REQUEST.statusCode

    override fun apiError() = ApiError.INVALID_PARAMETER

    override fun userResponseMessage() = "$message"

    override fun details() = emptyMap<String, List<Any>>()
}
