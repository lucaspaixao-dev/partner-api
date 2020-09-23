package com.github.lucasschwenke.partnerapi.application.web.requests

import org.geojson.MultiPolygon
import org.geojson.Point

data class PartnerRequest(
    val tradingName: String,
    val ownerName: String,
    val document: String,
    val coverageArea: MultiPolygon,
    val address: Point
)
