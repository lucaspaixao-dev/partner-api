package com.github.lucasschwenke.partnerapi.application.web.controllers

import com.github.lucasschwenke.partnerapi.application.web.responses.HealthCheckResponse
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode

class HealthCheckController {

    fun health(call: ApplicationCall): HealthCheckResponse =
        HealthCheckResponse(
            status = "Ok",
            up = true
        ).also {
            call.response.status(HttpStatusCode.OK)
        }
}
