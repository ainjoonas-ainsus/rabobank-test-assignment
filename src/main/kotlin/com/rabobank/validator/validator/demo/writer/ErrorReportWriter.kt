package com.rabobank.validator.validator.demo.writer

import com.rabobank.validator.validator.demo.model.ValidationResult
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.StepExecutionListener
import org.springframework.batch.core.annotation.BeforeStep
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.lang.NonNull
import java.io.File

class ErrorReportWriter: ItemWriter<ValidationResult>, StepExecutionListener {
    private lateinit var fileName: String

    @BeforeStep
    override fun beforeStep(stepExecution: StepExecution) {
        val jobExecution = stepExecution.jobExecution
        fileName = jobExecution.executionContext.getString("fileName")
    }
    override fun write(@NonNull chunk: Chunk<out ValidationResult>) {
        val errorFile = File(fileName)

        chunk.filter { it.errors.isNotEmpty() }.forEach { validationResult ->
            errorFile.appendText(validationResult.errors.joinToString(", ", prefix = "Errors: ", postfix = "\n"))
            errorFile.appendText("Context: $validationResult \n\n")
        }
    }
}