package com.github.lucasschwenke.partnerapi.resources.repositories.extensions

import com.github.lucasschwenke.partnerapi.domain.partner.Partner
import com.github.lucasschwenke.partnerapi.resources.repositories.entities.PartnerEntity

fun PartnerEntity.toModel() =
    Partner(
        id = this.id,
        tradingName = this.tradingName,
        ownerName = this.ownerName,
        document = this.document,
        coverageArea = this.coverageArea,
        address = this.address
    )
