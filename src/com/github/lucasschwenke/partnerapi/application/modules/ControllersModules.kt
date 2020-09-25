package com.github.lucasschwenke.partnerapi.application.modules

import com.github.lucasschwenke.partnerapi.application.web.controllers.PartnerController
import org.koin.core.module.Module
import org.koin.dsl.module

val controllersModules: Module = module {
    single {
        PartnerController(
            partnerService = get(),
            objectMapper = get(),
            partnerValidator = get()
        )
    }
}
