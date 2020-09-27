package com.github.lucasschwenke.partnerapi.application.web.responses

import java.time.LocalDateTime

data class HealthCheckResponse(
    val status: String,
    val up: Boolean,
    val date: LocalDateTime
)
