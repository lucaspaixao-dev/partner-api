package com.github.lucasschwenke.partnerapi.domain.partner

import org.geojson.MultiPolygon
import org.geojson.Point

data class Partner(
    val id: String? = null,
    val tradingName: String,
    val ownerName: String,
    val document: String,
    val coverageArea: MultiPolygon,
    val address: Point
)
