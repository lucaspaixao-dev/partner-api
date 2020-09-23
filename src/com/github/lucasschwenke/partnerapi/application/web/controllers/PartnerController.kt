package com.github.lucasschwenke.partnerapi.application.web.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.lucasschwenke.partnerapi.application.web.extensions.toModel
import com.github.lucasschwenke.partnerapi.application.web.requests.PartnerRequest
import com.github.lucasschwenke.partnerapi.application.web.responses.PartnerResponse
import com.github.lucasschwenke.partnerapi.domain.logger.LoggableClass
import com.github.lucasschwenke.partnerapi.domain.services.PartnerService
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode

class PartnerController(
    private val partnerService: PartnerService,
    private val objectMapper: ObjectMapper
) {

    fun createPartner(partnerRequest: PartnerRequest, call: ApplicationCall): PartnerResponse =
        logger.debug(
            "A new request to create a new partner has been received: " +
                    objectMapper.writeValueAsString(partnerRequest)
        ).let {
            PartnerResponse(partnerService.create(partnerRequest.toModel()))
        }.also {
            call.response.status(HttpStatusCode.Created)
        }

    companion object: LoggableClass()
}