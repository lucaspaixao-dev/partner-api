package com.github.lucasschwenke.partnerapi.application.web.controllers

import com.github.lucasschwenke.partnerapi.application.web.responses.HealthCheckResponse
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import java.time.LocalDateTime

class HealthCheckController {

    fun health(call: ApplicationCall): HealthCheckResponse =
        HealthCheckResponse(
            status = "Ok",
            up = true,
            date = LocalDateTime.now()
        ).also {
            call.response.status(HttpStatusCode.OK)
        }
}
