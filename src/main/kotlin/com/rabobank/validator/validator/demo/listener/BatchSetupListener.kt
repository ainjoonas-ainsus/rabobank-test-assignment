package com.rabobank.validator.validator.demo.listener

import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class BatchSetupListener : JobExecutionListener {
    override fun beforeJob(jobExecution: JobExecution) {
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm")
        val uniqueFileName = "errorTxt_${LocalDateTime.now().format(formatter)}.txt"
        jobExecution.executionContext.putString("fileName", uniqueFileName)
    }
}
