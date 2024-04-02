package com.rabobank.validator.validator.demo.processor

import com.rabobank.validator.validator.demo.model.CustomerStatement
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.math.BigDecimal

class CustomerStatementProcessorTest {

    private val customerStatementProcessor = CustomerStatementProcessor()
    private val customerStatement = CustomerStatement().apply {
        reference = 1L
        accountNumber = "AB123456789"
        description = "Sample description"
        startBalance = BigDecimal("1000.00")
        mutation = BigDecimal("-150.00")
        endBalance = BigDecimal("850.00")
    }

    @Test
    fun shouldProcessCorrectlyBuiltStatement() {
        assertEquals(customerStatementProcessor.process(customerStatement).errors.count(), 0)
    }

    @Test
    fun shouldFindReferenceError() {
        customerStatementProcessor.process(customerStatement)
        val validationResult = customerStatementProcessor.process(customerStatement)
        assertEquals(validationResult.errors.count(), 1)
        assertTrue(validationResult.errors[0].contains("Duplicate reference found"))
    }

    @Test
    fun shouldFindFalseBalanceError() {
        val customerStatement = CustomerStatement().apply {
            reference = 2L
            accountNumber = "AB123456789"
            description = "Sample description"
            startBalance = BigDecimal("1000.00")
            mutation = BigDecimal("-150.00")
            endBalance = BigDecimal("1150.00")
        }

        val validationResult = customerStatementProcessor.process(customerStatement)
        assertEquals(validationResult.errors.count(), 1)
        assertTrue(validationResult.errors[0].contains("Invalid end balance"))
    }
}