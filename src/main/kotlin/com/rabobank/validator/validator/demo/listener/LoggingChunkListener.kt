package com.rabobank.validator.validator.demo.listener

import org.slf4j.LoggerFactory
import org.springframework.batch.core.ChunkListener
import org.springframework.batch.core.annotation.OnReadError
import org.springframework.batch.core.annotation.OnWriteError

class LoggingChunkListener<T> : ChunkListener {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @OnReadError
    fun onReadError(exception: Exception) {
        logger.error("Read error: ${exception.message}")
    }

    @OnWriteError
    fun onWriteError(exception: Exception, items: List<T>) {
        logger.error("Write error: ${exception.message}, Failed items: $items")
    }
}