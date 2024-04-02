package com.rabobank.validator.validator.demo.reader

import com.rabobank.validator.validator.demo.model.CustomerStatement
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.batch.item.file.mapping.DefaultLineMapper
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.batch.item.xml.StaxEventItemReader
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ResourceLoader
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.core.io.support.ResourcePatternResolver
import org.springframework.oxm.jaxb.Jaxb2Marshaller
import org.springframework.stereotype.Component

@Component
class CustomerStatementReaderFactory(private val resourceLoader: ResourceLoader) {

    @Value("\${input-folder}")
    private lateinit var inputFolder: String

    fun buildReaderXml(): StaxEventItemReader<CustomerStatement> {
        val marshaller = Jaxb2Marshaller()
        marshaller.setClassesToBeBound(CustomerStatement::class.java)
        val resolver: ResourcePatternResolver = PathMatchingResourcePatternResolver(resourceLoader)
        val resources = resolver.getResources("${inputFolder}*.xml")

        return resources.firstOrNull()?.let { resource ->
            StaxEventItemReaderBuilder<CustomerStatement>()
                    .name("customerStatementItemReader")
                    .resource(resource) // Adjust the path to your XML file
                    .addFragmentRootElements("record")
                    .unmarshaller(marshaller)
                    .build()
        } ?: throw IllegalStateException("No files found in input folder.")
    }

    fun buildReaderCsv(): FlatFileItemReader<CustomerStatement> {

        val resolver: ResourcePatternResolver = PathMatchingResourcePatternResolver(resourceLoader)
        val resources = resolver.getResources("${inputFolder}*.csv")
        return resources.firstOrNull()?.let { resource ->
            FlatFileItemReader<CustomerStatement>().apply {
                setLinesToSkip(1)
                setResource(resource)
                setLineMapper(DefaultLineMapper<CustomerStatement>().apply {
                    setLineTokenizer(DelimitedLineTokenizer().apply {
                        setNames("reference", "accountNumber", "description", "startBalance", "mutation", "endBalance")
                    })
                    setFieldSetMapper(BeanWrapperFieldSetMapper<CustomerStatement>().apply {
                        setTargetType(CustomerStatement::class.java)
                    })
                })
            }
        } ?: throw IllegalStateException("No files found in input folder.")
    }
}