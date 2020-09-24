package com.github.lucasschwenke.partnerapi.domain.repositories

import com.github.lucasschwenke.partnerapi.domain.partner.Partner

interface PartnerRepository {

    fun insert(partner: Partner): Partner
    fun findById(id: String): Partner?
    fun findNearest(latitude: Double, longitude: Double): Partner?
}
