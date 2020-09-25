package com.github.lucasschwenke.partnerapi.domain.exceptions

class InvalidRequestException(
    message: String,
    private val details: Map<String, List<Any>>
) : ApiException(message = message) {

    override fun httpStatus() = HttpStatus.BAD_REQUEST.statusCode

    override fun apiError() = ApiError.INVALID_REQUEST

    override fun userResponseMessage() = "$message"

    override fun details() = details
}
