package com.github.lucasschwenke.partnerapi.domain.services

import com.github.lucasschwenke.partnerapi.domain.exceptions.PartnerAlreadyRegisteredException
import com.github.lucasschwenke.partnerapi.domain.exceptions.PartnerNotFoundException
import com.github.lucasschwenke.partnerapi.domain.partner.Partner
import com.github.lucasschwenke.partnerapi.domain.repositories.PartnerRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class PartnerServiceTest {

    private val partnerRepositoryMock = mockk<PartnerRepository>(relaxed = true)
    private val partnerService = PartnerService(partnerRepositoryMock)

    private val partnerMock = Partner(
        tradingName = "Zé",
        ownerName = "Zé 2",
        document = "123",
        coverageArea = mockk(relaxed = true),
        address = mockk(relaxed = true)
    )

    @Test
    fun `should create a new partner when there is not a partner with the same document already registered`() {
        every { partnerRepositoryMock.findByDocument(partnerMock.document) } returns null
        every { partnerRepositoryMock.insert(partnerMock) } returns partnerMock

        assertDoesNotThrow {
            partnerService.create(partnerMock)
        }

        verify { partnerRepositoryMock.findByDocument(partnerMock.document) }
        verify { partnerRepositoryMock.insert(partnerMock) }
    }

    @Test
    fun `should not create a new partner when there is a partner with the same document already registered`() {
        every { partnerRepositoryMock.findByDocument(partnerMock.document) } returns partnerMock

        assertThrows<PartnerAlreadyRegisteredException>(
            message = "There is a partner with the follow document ${partnerMock.document} already registered."
        ) {
            partnerService.create(partnerMock)
        }

        verify { partnerRepositoryMock.findByDocument(partnerMock.document) }
        verify(exactly = 0) { partnerRepositoryMock.insert(partnerMock) }
    }

    @Test
    fun `should return an existent partner when there is any partner with the id informed registered`() {
        val id = "123"

        every { partnerRepositoryMock.findById(id) } returns partnerMock

        assertDoesNotThrow {
            partnerService.findBydId(id)
        }

        verify { partnerRepositoryMock.findById(id) }
    }

    @Test
    fun `should an exception when there is not any partner created with the id informed`() {
        val id = "123"

        every { partnerRepositoryMock.findById(id) } returns null

        assertThrows<PartnerNotFoundException>(
            message = "The partner with the id $id does not exists."
        ) {
            partnerService.findBydId(id)
        }

        verify { partnerRepositoryMock.findById(id) }
    }

    @Test
    fun `should return the nearest partner when there is any near in the latitude and longitude informed`() {
        val latitude = 123.00
        val longitude = 222.00

        every { partnerRepositoryMock.findByLatitudeAndLongitude(latitude, longitude) } returns partnerMock

        assertDoesNotThrow {
            partnerService.findByLatitudeAndLongitude(latitude, longitude)
        }

        verify { partnerRepositoryMock.findByLatitudeAndLongitude(latitude, longitude) }
    }

    @Test
    fun `should an exception when there is not any near in the latitude and longitude informed`() {
        val latitude = 123.00
        val longitude = 222.00

        every { partnerRepositoryMock.findByLatitudeAndLongitude(latitude, longitude) } returns null

        assertThrows<PartnerNotFoundException>(
            message = "There is not any partner that covers the area of the latitude and longitude informed."
        ) {
            partnerService.findByLatitudeAndLongitude(latitude, longitude)
        }

        verify { partnerRepositoryMock.findByLatitudeAndLongitude(latitude, longitude) }
    }
}
