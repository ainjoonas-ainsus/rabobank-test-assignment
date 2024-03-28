package com.rabobank.validator.validator.demo.processor

import com.rabobank.validator.validator.demo.model.CustomerStatement
import com.rabobank.validator.validator.demo.model.ValidationResult
import org.springframework.batch.item.ItemProcessor

class CustomerStatementProcessor : ItemProcessor<CustomerStatement, ValidationResult> {
    // quick solution to reference check. This is basically a process of throwing sth into memory without care.
    // The reality is that this solution can kneecap the end result as we are using local memory for something we described to be possibly gigabytes in size.
    // Since this reference accounts for quite a bit of the overall data we use, it can become cumbersome in relation to RAM use.
    // In a production setting, this check would move quickly to somewhere we can access quickly, be it a redis instance or even a local file.
    // To reduce the amount of IO in this demo though, i chose to represent a solution like this as it would be the most similar to the approach i would take - use memory.
    private val seenReferences = mutableSetOf<Long>()

    override fun process(item: CustomerStatement): ValidationResult {
        val errors = mutableListOf<String>()

        val endBalance = item.startBalance!! + item.mutation!!
        if (endBalance != item.endBalance!!) {
            errors.add("Invalid end balance for reference ${item.reference}, found end balance to be $endBalance")
        }

        if (!seenReferences.add(item.reference!!)) {
            errors.add("Duplicate reference found: ${item.reference}")
        }

        return ValidationResult(item, errors)
    }
}