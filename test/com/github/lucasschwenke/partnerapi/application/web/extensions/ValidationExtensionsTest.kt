package com.github.lucasschwenke.partnerapi.application.web.extensions

import com.github.lucasschwenke.partnerapi.application.web.validations.Validation
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ValidationExtensionsTest {

    @Test
    fun `the error message list should be empty when the field is not null or blank`() {
        val result = Validation(
            fieldName = "test",
            fieldValue = "test"
        ).isNullOrBlank()

        assertThat(result.errorMessageList).isEmpty()
    }

    @Test
    fun `the error message list should be not empty when the field is null or blank`() {
        val result = Validation(
            fieldName = "test",
            fieldValue = "  "
        ).isNullOrBlank()

        assertThat(result.errorMessageList).isNotEmpty
        assertThat(result.errorMessageList.first()).isEqualTo("must not be empty or null.")
    }

    @Test
    fun `the error message list should be empty when the field is not null`() {
        val result = Validation(
            fieldName = "test2",
            fieldValue = 111
        ).isNull()

        assertThat(result.errorMessageList).isEmpty()
    }

    @Test
    fun `the error message list should be not empty when the field is null`() {
        val result = Validation(
            fieldName = "test2",
            fieldValue = null
        ).isNull()

        assertThat(result.errorMessageList).isNotEmpty
        assertThat(result.errorMessageList.first()).isEqualTo("must not be null.")
    }

    @Test
    fun `the error message list should be empty when the field is a valid CNPJ`() {
        val result = Validation(
            fieldName = "test3",
            fieldValue = "09458082000197"
        ).isInvalidCNPJ()

        assertThat(result.errorMessageList).isEmpty()
    }

    @Test
    fun `the error message list should not be empty when the field is a invalid CNPJ`() {
        val result = Validation(
            fieldName = "test3",
            fieldValue = "0945802282000197"
        ).isInvalidCNPJ()

        assertThat(result.errorMessageList).isNotEmpty
        assertThat(result.errorMessageList.first()).isEqualTo("the CNPJ is invalid.")
    }

    @Test
    fun `the error message list should not be empty when the field is a invalid CNPJ with letters`() {
        val result = Validation(
            fieldName = "test3",
            fieldValue = "ze"
        ).isInvalidCNPJ()

        assertThat(result.errorMessageList).isNotEmpty
        assertThat(result.errorMessageList.first()).isEqualTo("the CNPJ is invalid.")
    }
}
