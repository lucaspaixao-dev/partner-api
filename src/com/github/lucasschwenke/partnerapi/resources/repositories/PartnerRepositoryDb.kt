package com.github.lucasschwenke.partnerapi.resources.repositories

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.lucasschwenke.partnerapi.domain.logger.LoggableClass
import com.github.lucasschwenke.partnerapi.domain.partner.Partner
import com.github.lucasschwenke.partnerapi.domain.repositories.PartnerRepository
import com.github.lucasschwenke.partnerapi.resources.repositories.entities.PartnerEntity
import com.mongodb.MongoClient
import com.mongodb.client.MongoCollection
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

    companion object: LoggableClass()
}
