package com.github.lucasschwenke.partnerapi.domain.exceptions

class InvalidParameterException(message: String) : ApiException(message = message) {

    override fun httpStatus() = ApiError.BAD_REQUEST.statusCode

    override fun apiError() = ApiError.BAD_REQUEST

    override fun userResponseMessage() = "$message"
}
