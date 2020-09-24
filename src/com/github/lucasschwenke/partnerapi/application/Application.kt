package com.github.lucasschwenke.partnerapi.application

import com.github.lucasschwenke.partnerapi.application.config.configureObjectMapper
import com.github.lucasschwenke.partnerapi.application.modules.applicationModules
import com.github.lucasschwenke.partnerapi.application.modules.controllersModules
import com.github.lucasschwenke.partnerapi.application.modules.databaseModule
import com.github.lucasschwenke.partnerapi.application.modules.repositoriesModules
import com.github.lucasschwenke.partnerapi.application.modules.servicesModules
import com.github.lucasschwenke.partnerapi.application.web.controllers.PartnerController
import com.github.lucasschwenke.partnerapi.application.web.requests.PartnerRequest
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.util.KtorExperimentalAPI
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalAPI
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        jackson {
            configureObjectMapper()
        }

        install(Koin) {
            modules(
                listOf(
                    applicationModules,
                    databaseModule,
                    repositoriesModules,
                    servicesModules,
                    controllersModules
                )
            )
        }

        install(StatusPages) {

        }
    }

    val partnerController: PartnerController by inject()

    routing {
        route("/partner") {
            get {
                call.respond(partnerController.findNearest(this.call))
            }
            post {
                this.call.receive<PartnerRequest>().let {
                    call.respond(partnerController.createPartner(it, this.call))
                }
            }
            get("/{id}") {
                call.respond(partnerController.findById(this.call))
            }
        }
    }
}

