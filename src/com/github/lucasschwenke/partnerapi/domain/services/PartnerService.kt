package com.github.lucasschwenke.partnerapi.domain.services

import com.github.lucasschwenke.partnerapi.domain.exceptions.PartnerAlreadyRegisteredException
import com.github.lucasschwenke.partnerapi.domain.exceptions.PartnerNotFoundException
import com.github.lucasschwenke.partnerapi.domain.logger.LoggableClass
import com.github.lucasschwenke.partnerapi.domain.partner.Partner
import com.github.lucasschwenke.partnerapi.domain.repositories.PartnerRepository

class PartnerService(
    private val partnerRepository: PartnerRepository
) {

    fun create(partner: Partner): Partner {
        partnerRepository.findByDocument(partner.document)?.run {
            throw PartnerAlreadyRegisteredException(
                "There is a partner with the follow document ${partner.document} already registered."
            )
        }

        return partnerRepository.insert(partner)
    }

    fun findBydId(id: String): Partner =
        partnerRepository.findById(id) ?: throw PartnerNotFoundException("The partner with the id $id not found.")

    fun findByLatitudeAndLongitude(latitude: Double, longitude: Double): Partner =
        partnerRepository.findByLatitudeAndLongitude(latitude, longitude)
            ?: throw PartnerNotFoundException(
                "There is not any partner that covers the area of the latitude and longitude informed."
            )

    companion object : LoggableClass()
}
