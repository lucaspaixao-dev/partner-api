package componentTests.partner

import com.fasterxml.jackson.module.kotlin.readValue
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
class GetPartnerByIdComponentTest : ComponentTest() {

    @Test
    fun `should return 200 status code when there is any partner with the id informed`() {
        withTestApplication({ module(dbTestModule = getTestDbModule()) }) {
            var id = ""

            handleRequest(HttpMethod.Post, "/partner") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(readFile("requests/create_partner_json_request"))
            }.apply {
                val response = objectMapper.readValue<PartnerResponse>(this.response.content!!)
                id = response.id
            }

            handleRequest(HttpMethod.Get, "/partner/$id") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            }.apply {
                assertThat(this.response.status()).isNotNull
                assertThat(this.response.status()!!.value).isEqualTo(HttpStatusCode.OK.value)
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
    fun `should return 404 status code when there is not any partner with the id informed`() {
        withTestApplication({ module(dbTestModule = getTestDbModule()) }) {
            handleRequest(HttpMethod.Get, "/partner/asdasd") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            }.apply {
                assertThat(this.response.status()).isNotNull
                assertThat(this.response.status()!!.value).isEqualTo(HttpStatusCode.NotFound.value)
                assertThat(this.response.content).isNotNull()

                val expectedResponse = readFile("responses/partner_not_found_response")
                assertEquals(expectedResponse, this.response.content, JSONCompareMode.LENIENT)
            }
        }
    }
}
