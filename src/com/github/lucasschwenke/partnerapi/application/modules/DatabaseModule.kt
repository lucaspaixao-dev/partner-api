package com.github.lucasschwenke.partnerapi.application.modules

import com.github.lucasschwenke.partnerapi.application.config.DatabaseConfig
import io.ktor.util.KtorExperimentalAPI
import org.koin.core.module.Module
import org.koin.dsl.module

@KtorExperimentalAPI
val databaseModule: Module = module {
    single { DatabaseConfig.connect() }
}
