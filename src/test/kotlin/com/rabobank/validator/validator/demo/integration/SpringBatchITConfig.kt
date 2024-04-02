package com.rabobank.validator.validator.demo.integration

import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@TestConfiguration
class SpringBatchITConfig {

    @Bean
    fun jobLauncherTestUtils() = JobLauncherTestUtils()
}