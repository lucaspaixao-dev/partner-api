package com.github.lucasschwenke.partnerapi.domain.exceptions

enum class ApiError(val statusCode: Int) {
    BAD_REQUEST(400),
    NOT_FOUND(404)
}
