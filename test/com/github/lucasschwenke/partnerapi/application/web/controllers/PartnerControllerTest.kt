package com.github.lucasschwenke.partnerapi.application.web.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.lucasschwenke.partnerapi.application.web.requests.PartnerRequest
import com.github.lucasschwenke.partnerapi.application.web.validations.PartnerValidator
import com.github.lucasschwenke.partnerapi.domain.exceptions.InvalidParameterException
import com.github.lucasschwenke.partnerapi.domain.exceptions.InvalidRequestException
import com.github.lucasschwenke.partnerapi.domain.partner.Partner
import com.github.lucasschwenke.partnerapi.domain.services.PartnerService
import io.ktor.application.ApplicationCall
import io.ktor.http.HttpStatusCode
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class PartnerControllerTest {

    private val partnerServiceMock = mockk<PartnerService>(relaxed = true)
    private val objectMapperMock = mockk<ObjectMapper>(relaxed = true)
    private val partnerValidatorMock = mockk<PartnerValidator>(relaxed = true)

    private val partnerController = PartnerController(
        partnerServiceMock,
        objectMapperMock,
        partnerValidatorMock
    )

    private val applicationCallMock = mockk<ApplicationCall>(relaxed = true)
    private val partnerMock = Partner(
        id = "12",
        tradingName = "Test",
        ownerName = "Test2",
        document = "123",
        coverageArea = mockk(relaxed = true),
        address = mockk(relaxed = true)
    )
    private val partnerRequestMock = PartnerRequest(
        tradingName = "Test",
        ownerName = "Test2",
        document = "123",
        coverageArea = mockk(relaxed = true),
        address = mockk(relaxed = true)
    )

    @Test
    fun `should return Created status code when the request is valid`() {
        every { partnerValidatorMock.validate(any()) } returns emptyMap()
        every { partnerServiceMock.create(any()) } returns partnerMock

        val response = assertDoesNotThrow {
            partnerController.createPartner(partnerRequestMock, applicationCallMock)
        }

        verify { partnerValidatorMock.validate(any()) }
        verify { partnerServiceMock.create(any()) }
        verify { applicationCallMock.response.status(HttpStatusCode.Created) }

        assertThat(response).isNotNull
        assertThat(response.id).isEqualTo(partnerMock.id)
        assertThat(response.tradingName).isEqualTo(partnerMock.tradingName)
        assertThat(response.ownerName).isEqualTo(partnerMock.ownerName)
        assertThat(response.document).isEqualTo(partnerMock.document)
        assertThat(response.coverageArea).isNotNull
        assertThat(response.address).isNotNull
    }

    @Test
    fun `should thrown an exception when there is any invalid field on request`() {
        every { partnerValidatorMock.validate(any()) } returns mapOf("test" to listOf("test"))

        assertThrows<InvalidRequestException>(
            message = "The follow fields are invalids:"
        ) {
            partnerController.createPartner(partnerRequestMock, applicationCallMock)
        }

        verify { partnerValidatorMock.validate(any()) }
        verify(exactly = 0) { partnerServiceMock.create(any()) }
        verify(exactly = 0) { applicationCallMock.response.status(HttpStatusCode.Created) }
    }

    @Test
    fun `should return Ok status code when there is any partner already registered with the id informed`() {
        val id = "22"

        every { applicationCallMock.parameters["id"] } returns id
        every { partnerServiceMock.findBydId(id) } returns partnerMock

        val response = assertDoesNotThrow {
            partnerController.findById(applicationCallMock)
        }

        verify { applicationCallMock.parameters["id"] }
        verify { partnerServiceMock.findBydId(id) }
        verify { applicationCallMock.response.status(HttpStatusCode.OK) }

        assertThat(response).isNotNull
        assertThat(response.id).isEqualTo(partnerMock.id)
        assertThat(response.tradingName).isEqualTo(partnerMock.tradingName)
        assertThat(response.ownerName).isEqualTo(partnerMock.ownerName)
        assertThat(response.document).isEqualTo(partnerMock.document)
        assertThat(response.coverageArea).isNotNull
        assertThat(response.address).isNotNull
    }

    @Test
    fun `should thrown an exception when the parameter id has been not informed on request`() {
        every { applicationCallMock.parameters["id"] } returns null

        assertThrows<InvalidParameterException>(
            message = "The parameter id does not informed."
        ) {
            partnerController.findById(applicationCallMock)
        }

        verify { applicationCallMock.parameters["id"] }
        verify(exactly = 0) { partnerServiceMock.findBydId(any()) }
        verify(exactly = 0) { applicationCallMock.response.status(HttpStatusCode.OK) }
    }

    @Test
    fun `should return Ok status code when there is any partner already registered with the latitude and longitude informed`() {
        val latitude = "22.22"
        val longitude = "11.22"

        every { applicationCallMock.request.queryParameters["latitude"] } returns latitude
        every { applicationCallMock.request.queryParameters["longitude"] } returns longitude
        every { partnerServiceMock.findByLatitudeAndLongitude(any(), any()) } returns partnerMock

        val response = assertDoesNotThrow {
            partnerController.findByLatitudeAndLongitude(applicationCallMock)
        }

        verify { applicationCallMock.request.queryParameters["latitude"] }
        verify { applicationCallMock.request.queryParameters["longitude"] }
        verify { partnerServiceMock.findByLatitudeAndLongitude(any(), any()) }
        verify { applicationCallMock.response.status(HttpStatusCode.OK) }

        assertThat(response).isNotNull
        assertThat(response.id).isEqualTo(partnerMock.id)
        assertThat(response.tradingName).isEqualTo(partnerMock.tradingName)
        assertThat(response.ownerName).isEqualTo(partnerMock.ownerName)
        assertThat(response.document).isEqualTo(partnerMock.document)
        assertThat(response.coverageArea).isNotNull
        assertThat(response.address).isNotNull
    }

    @Test
    fun `should not thrown an exception when the query parameter latitude has been not informed on request`() {
        val longitude = "11.22"

        every { applicationCallMock.request.queryParameters["latitude"] } returns null
        every { applicationCallMock.request.queryParameters["longitude"] } returns longitude
        every { partnerServiceMock.findByLatitudeAndLongitude(0.0, 11.22) } returns partnerMock

        assertDoesNotThrow {
            partnerController.findByLatitudeAndLongitude(applicationCallMock)
        }

        verify { applicationCallMock.request.queryParameters["latitude"] }
        verify { applicationCallMock.request.queryParameters["longitude"] }
        verify { partnerServiceMock.findByLatitudeAndLongitude(0.0, 11.22) }
        verify { applicationCallMock.response.status(HttpStatusCode.OK) }
    }

    @Test
    fun `should thrown an exception when the query parameter longitude has been not informed on request`() {
        val latitude = "22.22"

        every { applicationCallMock.request.queryParameters["latitude"] } returns latitude
        every { applicationCallMock.request.queryParameters["longitude"] } returns null
        every { partnerServiceMock.findByLatitudeAndLongitude(22.22, 0.0) } returns partnerMock

        assertDoesNotThrow {
            partnerController.findByLatitudeAndLongitude(applicationCallMock)
        }

        verify { applicationCallMock.request.queryParameters["latitude"] }
        verify { applicationCallMock.request.queryParameters["longitude"] }
        verify { partnerServiceMock.findByLatitudeAndLongitude(22.22, 0.0) }
        verify { applicationCallMock.response.status(HttpStatusCode.OK) }
    }
}
