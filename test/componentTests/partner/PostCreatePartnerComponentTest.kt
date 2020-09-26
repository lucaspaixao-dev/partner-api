package componentTests.partner

import componentTests.ComponentTest
import io.ktor.server.testing.withTestApplication
import io.ktor.util.KtorExperimentalAPI
import org.junit.jupiter.api.Test
import com.github.lucasschwenke.partnerapi.application.module

@KtorExperimentalAPI
class PostCreatePartnerComponentTest : ComponentTest() {

    @Test
    fun `should return 201 status code when the partner was created`() {
        withTestApplication({ module(dbTestModule = getTestDbModule()) }) {

        }
    }
}