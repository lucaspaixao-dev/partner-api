package com.github.lucasschwenke.partnerapi.domain.services

import com.github.lucasschwenke.partnerapi.domain.logger.LoggableClass
import com.github.lucasschwenke.partnerapi.domain.partner.Partner
import com.github.lucasschwenke.partnerapi.domain.repositories.PartnerRepository

class PartnerService(
    private val partnerRepository: PartnerRepository
) {

    fun create(partner: Partner): Partner {
        return partnerRepository.insert(partner)
    }

    fun findBydId(id: String): Partner =
        partnerRepository.findById(id) ?: throw RuntimeException("The partner with the id $id does not exists.")

    fun findNearest(latitude: Double, longitude: Double): Partner {
        return partnerRepository.findNearest(latitude, longitude) ?:
            throw RuntimeException("Test")
    }

    companion object: LoggableClass()
}
