package componentTests

import com.github.lucasschwenke.partnerapi.application.config.DatabaseConfig
import com.mongodb.BasicDBObject
import com.mongodb.MongoClient
import com.mongodb.ServerAddress
import de.bwaldvogel.mongo.MongoServer
import de.bwaldvogel.mongo.backend.memory.MemoryBackend
import io.ktor.util.KtorExperimentalAPI
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.koin.core.module.Module
import org.koin.dsl.module

@KtorExperimentalAPI
abstract class ComponentTest: BeforeEachCallback, AfterEachCallback {

    private val server: MongoServer = MongoServer(MemoryBackend())
    private val client: MongoClient = MongoClient(ServerAddress(server.bind()))

    private val databaseConfig = mockk<DatabaseConfig>(relaxed = true)

    override fun beforeEach(context: ExtensionContext?) {
        every { databaseConfig.getDatabase() } returns DB_NAME
    }

    override fun afterEach(context: ExtensionContext?) {
        client.getDatabase(DB_NAME).getCollection("dna")
            .deleteMany(BasicDBObject())
    }

    protected fun getTestDbModule(): Module = module {
        single { client }
    }

    companion object {
        private const val DB_NAME = "fake-db"
    }
}
