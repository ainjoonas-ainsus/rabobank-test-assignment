package com.rabobank.validator.validator.demo

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.boot.CommandLineRunner
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.stereotype.Component

@Component
class AppStartupRunner(
        private val jobLauncher: JobLauncher,
        private val job: Job,
        private val appContext: ConfigurableApplicationContext // Inject the application context
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        val execution = jobLauncher.run(job, JobParameters())

        if (execution.status.isUnsuccessful) {
            println("Job failed with status: ${execution.status}")
        } else {
            println("Job completed successfully")
        }

        appContext.close()
    }
}