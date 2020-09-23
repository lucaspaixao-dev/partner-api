package com.github.lucasschwenke.partnerapi.application.modules

import com.github.lucasschwenke.partnerapi.domain.services.PartnerService
import org.koin.core.module.Module
import org.koin.dsl.module

val servicesModules: Module = module {
    single {
        PartnerService(
            partnerRepository = get()
        )
    }
}
