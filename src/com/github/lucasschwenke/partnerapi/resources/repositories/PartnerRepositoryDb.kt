package com.github.lucasschwenke.partnerapi.resources.repositories

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.lucasschwenke.partnerapi.domain.logger.LoggableClass
import com.github.lucasschwenke.partnerapi.domain.partner.Partner
import com.github.lucasschwenke.partnerapi.domain.repositories.PartnerRepository
import com.github.lucasschwenke.partnerapi.resources.repositories.entities.PartnerEntity
import com.github.lucasschwenke.partnerapi.resources.repositories.extensions.toModel
import com.mongodb.BasicDBObject
import com.mongodb.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Indexes
import com.mongodb.client.model.Projections
import io.azam.ulidj.ULID
import org.bson.Document as BsonDocument

class PartnerRepositoryDb(
    client: MongoClient,
    database: String,
    private val objectMapper: ObjectMapper
) : PartnerRepository {

    private val collection: MongoCollection<BsonDocument>

    init {
        val db = client.getDatabase(database)
        collection = db.getCollection("partner")
        collection.createIndex(Indexes.geo2dsphere("address"))

        logger.debug("Connect in database $database on collection $collection")
    }

    override fun insert(partner: Partner): Partner {
        val id = ULID.random().toString()

        val partnerEntity = PartnerEntity(
            id = id,
            tradingName = partner.tradingName,
            ownerName = partner.ownerName,
            document = partner.document,
            coverageArea = partner.coverageArea,
            address = partner.address
        )

        logger.debug("Creating a new partner with the name ${partnerEntity.ownerName}")

        val insertJson = objectMapper.writeValueAsString(partnerEntity)
        val document = BsonDocument.parse(insertJson)
        collection.insertOne(document)

        return partner.copy(id = id).also {
            logger.debug("Partner has been created with id $id")
        }
    }

    override fun findById(id: String): Partner? =
        logger.debug("Searching partner by id $id").run {
            BasicDBObject(mutableMapOf("id" to id).toMap())
        }.let {
            collection.find(it).projection(Projections.excludeId()).firstOrNull()?.let { document ->
                val jsonResult = document.toJson()
                logger.debug("Has found the follow partner with the id $id: $jsonResult")
                objectMapper.readValue<PartnerEntity>(jsonResult).toModel()
            }
        }

    override fun findByDocument(document: String): Partner? =
        logger.debug("Searching partner by document $document").run {
            BasicDBObject(mutableMapOf("document" to document).toMap())
        }.let {
            collection.find(it).projection(Projections.excludeId()).firstOrNull()?.let { document ->
                val jsonResult = document.toJson()
                logger.debug("Has found the follow partner with the document $document: $jsonResult")
                objectMapper.readValue<PartnerEntity>(jsonResult).toModel()
            }
        }

    override fun findByLatitudeAndLongitude(latitude: Double, longitude: Double): Partner? =
        collection.aggregate(
            listOf(
                BsonDocument(
                    "\$geoNear",
                    BsonDocument(
                        "near",
                        BsonDocument("type", "Point").append("coordinates", listOf(latitude, longitude))
                    ).append("distanceField", "partner.calculated").append(
                        "query",
                        BsonDocument(
                            "coverage_area",
                            BsonDocument(
                                "\$geoIntersects",
                                BsonDocument(
                                    "\$geometry",
                                    BsonDocument("type", "Point")
                                        .append("coordinates", listOf(latitude, longitude))
                                )
                            )
                        )
                    ).append("spherical", true)
                )
            )
        ).firstOrNull()?.let { document ->
            val jsonResult = document.toJson()
            objectMapper.readValue<PartnerEntity>(jsonResult).toModel().also {
                logger.debug(
                    "Has found the follow partner nearest to latitude $latitude and longitude $longitude: ${it.ownerName}"
                )
            }
        }

    companion object : LoggableClass()
}
