package com.github.lucasschwenke.partnerapi.application.modules

import com.github.lucasschwenke.partnerapi.application.config.configureObjectMapper
import org.koin.core.module.Module
import org.koin.dsl.module

val applicationModules: Module = module {
    single { configureObjectMapper() }
}
