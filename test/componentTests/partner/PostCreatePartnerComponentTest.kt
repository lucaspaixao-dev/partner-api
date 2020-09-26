package componentTests.partner

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.lucasschwenke.partnerapi.application.config.configureObjectMapper
import com.github.lucasschwenke.partnerapi.application.module
import com.github.lucasschwenke.partnerapi.application.web.responses.PartnerResponse
import componentTests.ComponentTest
import componentTests.utils.ComponentTestsUtils.readFile
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import io.ktor.util.KtorExperimentalAPI
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert.assertEquals
import org.skyscreamer.jsonassert.JSONCompareMode

@KtorExperimentalAPI
class PostCreatePartnerComponentTest : ComponentTest() {

    private val objectMapper: ObjectMapper = configureObjectMapper()

    @Test
    fun `should return 201 status code when the partner was created`() {
        withTestApplication({ module(dbTestModule = getTestDbModule()) }) {
            handleRequest(HttpMethod.Post, "/partner") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(readFile("requests/create_partner_json_request"))
            }.apply {
                assertThat(this.response.status()).isNotNull
                assertThat(this.response.status()!!.value).isEqualTo(HttpStatusCode.Created.value)
                assertThat(this.response.content).isNotNull()

                val response = objectMapper.readValue<PartnerResponse>(this.response.content!!)
                assertThat(response.id).isNotNull()
                assertThat(response.tradingName).isEqualTo("Adega Moinho Velho")
                assertThat(response.ownerName).isEqualTo("Alguem")
                assertThat(response.document).isEqualTo("62739478000195")
                assertThat(response.coverageArea).isNotNull
                assertThat(response.address).isNotNull
            }
        }
    }

    @Test
    fun `should return 400 status code when there is a partner with the same document already registered`() {
        withTestApplication({ module(dbTestModule = getTestDbModule()) }) {
            handleRequest(HttpMethod.Post, "/partner") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(readFile("requests/create_partner_json_request"))
            }

            handleRequest(HttpMethod.Post, "/partner") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(readFile("requests/create_partner_json_request"))
            }.apply {
                assertThat(this.response.status()).isNotNull
                assertThat(this.response.status()!!.value).isEqualTo(HttpStatusCode.BadRequest.value)
                assertThat(this.response.content).isNotNull()

                val expectedResponse = readFile("responses/partner_already_registered_response")
                assertEquals(expectedResponse, this.response.content, JSONCompareMode.LENIENT)
            }
        }
    }

    @Test
    fun `should return 400 status code when there is any invalid field in request`() {
        withTestApplication({ module(dbTestModule = getTestDbModule()) }) {
            handleRequest(HttpMethod.Post, "/partner") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(readFile("requests/invalid_partner_request"))
            }.apply {
                assertThat(this.response.status()).isNotNull
                assertThat(this.response.status()!!.value).isEqualTo(HttpStatusCode.BadRequest.value)
                assertThat(this.response.content).isNotNull()

                val expectedResponse = readFile("responses/invalid_request_response")
                assertEquals(expectedResponse, this.response.content, JSONCompareMode.LENIENT)
            }
        }
    }
}
