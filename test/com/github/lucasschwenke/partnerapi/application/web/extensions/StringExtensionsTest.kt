package com.github.lucasschwenke.partnerapi.application.web.extensions

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StringExtensionsTest {

    @Test
    fun `should return only numbers when the string contains special characters`() {
        val response = "25.408.621/0001-30".onlyNumbersAndLetters()

        assertThat(response).isNotBlank()
        assertThat(response).isEqualTo("25408621000130")
    }
}
