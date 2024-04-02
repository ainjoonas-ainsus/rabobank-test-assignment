package com.rabobank.validator.validator.demo.integration

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.io.File

@SpringBootTest
@SpringBatchTest
class CustomerStatementValidatorConfigIT {
    @Autowired
    private lateinit var jobLauncherTestUtils: JobLauncherTestUtils

    @Test
    fun testEntireJob() {
        val jobExecution = jobLauncherTestUtils.launchJob()
        assertEquals(BatchStatus.COMPLETED, jobExecution.status)

        deleteFilesWithTestInName("./")
        // Additional assertions as needed, for example, checking the job's ExecutionContext or the database state
    }

    fun deleteFilesWithTestInName(directoryPath: String) {
        // Create a File object for the directory
        val directory: File = File(directoryPath)

        // Ensure the specified path is a directory
        if (directory.isDirectory()) {
            // List all files in the directory that have "test" in their names
            val filesToDelete: Array<File> = directory.listFiles { _, name -> name.contains("test") } ?: emptyArray()

            // Iterate over the filtered files and delete each one
            for (file in filesToDelete) {
                file.delete()
            }
        } else {
            println("$directoryPath is not a directory.")
        }
    }


}