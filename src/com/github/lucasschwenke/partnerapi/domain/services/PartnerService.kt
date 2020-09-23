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

    companion object: LoggableClass()
}
