package com.github.lucasschwenke.partnerapi.application.modules

import com.github.lucasschwenke.partnerapi.application.config.DatabaseConfig
import com.github.lucasschwenke.partnerapi.domain.repositories.PartnerRepository
import com.github.lucasschwenke.partnerapi.resources.repositories.PartnerRepositoryDb
import io.ktor.util.KtorExperimentalAPI
import org.koin.core.module.Module
import org.koin.dsl.module

@KtorExperimentalAPI
val repositoriesModules: Module = module {
    single<PartnerRepository> {
        PartnerRepositoryDb(
            client = get(),
            database = DatabaseConfig.getDatabase(),
            objectMapper = get()
        )
    }
}
