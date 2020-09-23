package com.github.lucasschwenke.partnerapi.domain.logger

import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class LoggableClass {
    val logger: Logger = LoggerFactory.getLogger(this.javaClass)
}
