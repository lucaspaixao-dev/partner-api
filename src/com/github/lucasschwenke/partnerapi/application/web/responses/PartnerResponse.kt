package com.github.lucasschwenke.partnerapi.application.web.responses

import com.github.lucasschwenke.partnerapi.domain.partner.Partner
import org.geojson.MultiPolygon
import org.geojson.Point

data class PartnerResponse(
    val id: String,
    val tradingName: String,
    val ownerName: String,
    val document: String,
    val coverageArea: MultiPolygon,
    val address: Point
) {

    constructor(partner: Partner): this(
        id = partner.id!!,
        tradingName = partner.tradingName,
        ownerName = partner.ownerName,
        document = partner.document,
        coverageArea = partner.coverageArea,
        address = partner.address
    )
}
