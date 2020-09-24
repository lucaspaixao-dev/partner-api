package com.github.lucasschwenke.partnerapi.application.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

fun configureObjectMapper(): ObjectMapper = ObjectMapper().apply {
    propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
}.let {
    it.enable(SerializationFeature.INDENT_OUTPUT)
    it.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    it.registerKotlinModule()
    it.setSerializationInclusion(JsonInclude.Include.NON_NULL)
}
