package com.github.lucasschwenke.partnerapi.application.web.responses

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.lucasschwenke.partnerapi.domain.partner.Partner
import org.geojson.MultiPolygon
import org.geojson.Point

data class PartnerResponse(
    @JsonProperty("id") val id: String,
    @JsonProperty("trading_name") val tradingName: String,
    @JsonProperty("owner_name") val ownerName: String,
    @JsonProperty("document") val document: String,
    @JsonProperty("coverage_area") val coverageArea: MultiPolygon,
    @JsonProperty("address") val address: Point
) {

    constructor(partner: Partner) : this(
        id = partner.id!!,
        tradingName = partner.tradingName,
        ownerName = partner.ownerName,
        document = partner.document,
        coverageArea = partner.coverageArea,
        address = partner.address
    )
}
