package com.github.lucasschwenke.partnerapi.domain.exceptions

class PartnerAlreadyRegisteredException(message: String) : ApiException(message = message) {

    override fun httpStatus() = HttpStatus.BAD_REQUEST.statusCode

    override fun apiError() = ApiError.PARTNER_ALREADY_REGISTERED

    override fun userResponseMessage() = "$message"

    override fun details() = emptyMap<String, List<Any>>()
}
