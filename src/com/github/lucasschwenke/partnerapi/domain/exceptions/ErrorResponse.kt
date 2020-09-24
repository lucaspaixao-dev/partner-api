package com.github.lucasschwenke.partnerapi.domain.exceptions

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorResponse(
    @JsonProperty("api_error") val apiError: ApiError,
    @JsonProperty("message") val message: String
)
