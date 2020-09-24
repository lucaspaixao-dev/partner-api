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
            "A new request to create a new partner has been received: ${getJsonString(partnerRequest)}"
        ).let {
            PartnerResponse(partnerService.create(partnerRequest.toModel()))
        }.also {
            call.response.status(HttpStatusCode.Created)
            logger.debug(
                "Replying ${HttpStatusCode.Created.value} with the follow json response " +
                        "${getJsonString(it)} in createPartner endpoint."
            )
        }

    fun findById(call: ApplicationCall): PartnerResponse {
        val id = call.parameters["id"] ?: throw RuntimeException("The id does not informed.")

        return PartnerResponse(partnerService.findBydId(id)).also {
            call.response.status(HttpStatusCode.OK)
            logger.debug(
                "Replying ${HttpStatusCode.OK} with the follow json response ${getJsonString(it)} in findById endpoint."
            )
        }
    }

    fun findNearest(call: ApplicationCall): PartnerResponse {
        val latitude = call.request.queryParameters["latitude"]?.toDouble()
            ?: throw RuntimeException("The latitude does not informed.")

        val longitude = call.request.queryParameters["latitude"]?.toDouble()
            ?: throw RuntimeException("The latitude does not informed.")

        return PartnerResponse(partnerService.findNearest(latitude, longitude)).also {
            call.response.status(HttpStatusCode.OK)
            logger.debug(
                "Replying ${HttpStatusCode.OK} with the follow json response " +
                        "${getJsonString(it)} in findNearest endpoint."
            )
        }
    }

    private fun getJsonString(any: Any): String = objectMapper.writeValueAsString(any)

    companion object: LoggableClass()
}