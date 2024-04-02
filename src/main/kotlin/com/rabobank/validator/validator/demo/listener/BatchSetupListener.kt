package com.rabobank.validator.validator.demo.listener

import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class BatchSetupListener : JobExecutionListener {

    @Value("\${output-name}")
    private lateinit var outputName: String
    override fun beforeJob(jobExecution: JobExecution) {
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm")
        val uniqueFileName = "${outputName}-${LocalDateTime.now().format(formatter)}.txt"
        jobExecution.executionContext.putString("fileName", uniqueFileName)
    }
}
