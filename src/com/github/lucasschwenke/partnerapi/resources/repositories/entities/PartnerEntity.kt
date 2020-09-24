package com.github.lucasschwenke.partnerapi.resources.repositories.entities

import org.geojson.MultiPolygon
import org.geojson.Point

data class PartnerEntity(
    val id: String,
    val tradingName: String,
    val ownerName: String,
    val document: String,
    val coverageArea: MultiPolygon,
    val address: Point
)
