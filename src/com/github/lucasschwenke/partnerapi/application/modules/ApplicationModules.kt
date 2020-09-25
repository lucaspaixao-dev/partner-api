package com.github.lucasschwenke.partnerapi.application.modules

import com.github.lucasschwenke.partnerapi.application.config.configureObjectMapper
import com.github.lucasschwenke.partnerapi.application.web.validations.PartnerValidator
import com.github.lucasschwenke.partnerapi.application.web.validations.Validator
import org.koin.core.module.Module
import org.koin.dsl.module

val applicationModules: Module = module {
    single { configureObjectMapper() }
}

val validatorsModules: Module = module {
    single<Validator> { PartnerValidator() }
}
