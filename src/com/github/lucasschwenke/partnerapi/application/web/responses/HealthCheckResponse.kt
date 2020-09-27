package com.github.lucasschwenke.partnerapi.application.web.responses

data class HealthCheckResponse(
    val status: String,
    val up: Boolean
)
