package com.rabobank.validator.validator.demo.config

import com.rabobank.validator.validator.demo.listener.BatchSetupListener
import com.rabobank.validator.validator.demo.model.CustomerStatement
import com.rabobank.validator.validator.demo.model.ValidationResult
import com.rabobank.validator.validator.demo.processor.CustomerStatementProcessor
import com.rabobank.validator.validator.demo.reader.CustomerStatementXmlReader
import com.rabobank.validator.validator.demo.reader.csvReader
import com.rabobank.validator.validator.demo.writer.ErrorReportWriter
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class CustomerStatementProcessorConfig {

    @Bean
    fun validateCustomerStatementsJob(jobRepository: JobRepository,
                                         transactionManager: PlatformTransactionManager): Job {
        fun validationStepCsv(jobRepository: JobRepository, transactionManager: PlatformTransactionManager): Step {
            return StepBuilder("validationStepCsv", jobRepository)
                    .chunk<CustomerStatement, ValidationResult>(10, transactionManager)
                    .reader(csvReader())
                    .writer(ErrorReportWriter())
                    .processor(CustomerStatementProcessor())
                    .build()
        }


        fun validationStepXml(jobRepository: JobRepository, transactionManager: PlatformTransactionManager): Step {
            return StepBuilder("validationStepXml", jobRepository)
                    .chunk<CustomerStatement, ValidationResult>(10, transactionManager)
                    .reader(CustomerStatementXmlReader().buildReader())
                    .writer(ErrorReportWriter())
                    .processor(CustomerStatementProcessor())
                    .build()
        }
        return JobBuilder("validateFileJob", jobRepository)
                .start(validationStepCsv(jobRepository, transactionManager))
                .next(validationStepXml(jobRepository, transactionManager))
                .listener(BatchSetupListener())
                .build()
    }
}
