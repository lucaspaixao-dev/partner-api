package com.github.lucasschwenke.partnerapi.domain.repositories

import com.github.lucasschwenke.partnerapi.domain.partner.Partner

interface PartnerRepository {

    fun insert(partner: Partner): Partner
}
