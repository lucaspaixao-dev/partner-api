package com.github.lucasschwenke.partnerapi.application.web.extensions

import com.github.lucasschwenke.partnerapi.application.web.requests.PartnerRequest
import com.github.lucasschwenke.partnerapi.domain.partner.Partner

fun PartnerRequest.toModel() =
    Partner(
        tradingName = this.tradingName,
        ownerName = this.ownerName,
        document = this.document,
        coverageArea = this.coverageArea,
        address = this.address
    )
