package com.rabobank.validator.validator.demo.reader

import com.rabobank.validator.validator.demo.model.CustomerStatement
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.batch.item.file.mapping.DefaultLineMapper
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.FileSystemResource

@Bean
fun csvReader(): FlatFileItemReader<CustomerStatement> {
    return FlatFileItemReader<CustomerStatement>().apply {
        setLinesToSkip(1)
        setResource(ClassPathResource("input/records.csv"))
        setLineMapper(DefaultLineMapper<CustomerStatement>().apply {
            setLineTokenizer(DelimitedLineTokenizer().apply {
                setNames("reference", "accountNumber", "description", "startBalance", "mutation", "endBalance")
            })
            setFieldSetMapper(BeanWrapperFieldSetMapper<CustomerStatement>().apply {
                setTargetType(CustomerStatement::class.java)
            })
        })
    }
}