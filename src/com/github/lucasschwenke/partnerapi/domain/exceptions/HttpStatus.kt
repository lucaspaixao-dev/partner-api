package com.github.lucasschwenke.partnerapi.domain.exceptions

enum class HttpStatus(val statusCode: Int) {
    BAD_REQUEST(400),
    NOT_FOUND(404)
}
