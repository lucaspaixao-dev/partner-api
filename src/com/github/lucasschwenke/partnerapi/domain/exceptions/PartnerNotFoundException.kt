package com.github.lucasschwenke.partnerapi.domain.exceptions

class PartnerNotFoundException(message: String) : ApiException(message = message) {

    override fun httpStatus() = HttpStatus.NOT_FOUND.statusCode

    override fun apiError() = ApiError.PARTNER_NOT_FOUND

    override fun userResponseMessage() = "$message"

    override fun details() = emptyMap<String, List<Any>>()
}
