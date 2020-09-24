package com.github.lucasschwenke.partnerapi.domain.exceptions

class PartnerNotFoundException(message: String) : ApiException(message = message) {

    override fun httpStatus() = ApiError.NOT_FOUND.statusCode

    override fun apiError() = ApiError.NOT_FOUND

    override fun userResponseMessage() = "$message"
}
