package com.rabobank.validator.validator.demo.integration

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
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

    @AfterEach
    fun cleanUpAfter() {
        deleteFilesWithTestInName("./")
    }
    @BeforeEach
    fun cleanUpBefore() {
        deleteFilesWithTestInName("./")
    }

    @Test
    fun shouldValidateFiles() {
        val jobExecution = jobLauncherTestUtils.launchJob()
        assertEquals(BatchStatus.COMPLETED, jobExecution.status)

        val directory = File("./")

        if (directory.isDirectory()) {
            val filesToProcess: Array<File> = directory.listFiles { _, name -> name.contains("test") } ?: emptyArray()

            for (file in filesToProcess) {
                if (file.isFile) {
                    val errorLines = file.readLines().filter { line -> line.contains("Errors:") }
                    assertEquals(errorLines.count(), 6)
                }
            }
        }
    }

    fun deleteFilesWithTestInName(directoryPath: String) {
        val directory = File(directoryPath)

        if (directory.isDirectory()) {
            val filesToDelete: Array<File> = directory.listFiles { _, name -> name.contains("test") } ?: emptyArray()

            for (file in filesToDelete) {
                file.delete()
            }
        } else {
            println("$directoryPath is not a directory.")
        }
    }


}