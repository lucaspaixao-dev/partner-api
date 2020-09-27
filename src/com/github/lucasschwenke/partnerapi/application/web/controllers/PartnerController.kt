package com.github.lucasschwenke.partnerapi.application.web.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.lucasschwenke.partnerapi.application.web.extensions.onlyNumbersAndLetters
import com.github.lucasschwenke.partnerapi.application.web.extensions.toModel
import com.github.lucasschwenke.partnerapi.application.web.requests.PartnerRequest
import com.github.lucasschwenke.partnerapi.application.web.responses.PartnerResponse
import com.github.lucasschwenke.partnerapi.application.web.validations.Validator
import com.github.lucasschwenke.partnerapi.domain.exceptions.InvalidParameterException
import com.github.lucasschwenke.partnerapi.domain.exceptions.InvalidRequestException
import com.github.lucasschwenke.partnerapi.domain.logger.LoggableClass
import com.github.lucasschwenke.partnerapi.domain.services.PartnerService
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode

class PartnerController(
    private val partnerService: PartnerService,
    private val objectMapper: ObjectMapper,
    private val partnerValidator: Validator
) {

    fun createPartner(partnerRequest: PartnerRequest, call: ApplicationCall): PartnerResponse =
        logger.debug(
            "A new request to create a new partner has been received: ${getJsonString(partnerRequest)}"
        ).let {
            val partner = partnerRequest.copy(document = partnerRequest.document.onlyNumbersAndLetters())
            val errors = partnerValidator.validate(partner)

            if (errors.isNotEmpty()) {
                logger.error("There are some invalids fields in request.")

                throw InvalidRequestException(
                    message = "The follow fields are invalids:",
                    details = errors
                )
            }

            PartnerResponse(partnerService.create(partner.toModel()))
        }.also {
            call.response.status(HttpStatusCode.Created)
            logger.debug(
                "Replying ${HttpStatusCode.Created.value} with the follow json response ${getJsonString(it)} in createPartner endpoint."
            )
        }

    fun findById(call: ApplicationCall): PartnerResponse {
        val id = call.parameters["id"]
            ?: throw InvalidParameterException("The parameter id does not informed.")

        return PartnerResponse(partnerService.findBydId(id)).also {
            call.response.status(HttpStatusCode.OK)
            logger.debug(
                "Replying ${HttpStatusCode.OK} with the follow json response ${getJsonString(it)} in findById endpoint."
            )
        }
    }

    fun findByLatitudeAndLongitude(call: ApplicationCall): PartnerResponse {
        val latitude = call.request.queryParameters["latitude"]?.toDouble() ?: DEFAULT_VALUE
        val longitude = call.request.queryParameters["longitude"]?.toDouble() ?: DEFAULT_VALUE

        return PartnerResponse(partnerService.findByLatitudeAndLongitude(latitude, longitude)).also {
            call.response.status(HttpStatusCode.OK)
            logger.debug(
                "Replying ${HttpStatusCode.OK} with the follow json response ${getJsonString(it)} in findNearest endpoint."
            )
        }
    }

    private fun getJsonString(any: Any): String = objectMapper.writeValueAsString(any)

    companion object : LoggableClass() {
        private const val DEFAULT_VALUE = 0.0
    }
}
