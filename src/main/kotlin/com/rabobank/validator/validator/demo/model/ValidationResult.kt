package com.rabobank.validator.validator.demo.model

data class ValidationResult(
        val customerStatement: CustomerStatement?,
        val errors: List<String>)