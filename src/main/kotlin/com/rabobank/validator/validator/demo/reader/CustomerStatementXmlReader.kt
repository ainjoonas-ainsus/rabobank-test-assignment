package com.rabobank.validator.validator.demo.reader

import com.rabobank.validator.validator.demo.model.CustomerStatement
import org.springframework.batch.item.xml.StaxEventItemReader
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder
import org.springframework.core.io.ClassPathResource
import org.springframework.oxm.jaxb.Jaxb2Marshaller

class CustomerStatementXmlReader() {
    fun buildReader(): StaxEventItemReader<CustomerStatement> {
        val marshaller = Jaxb2Marshaller()
        marshaller.setClassesToBeBound(CustomerStatement::class.java)

        return StaxEventItemReaderBuilder<CustomerStatement>()
                .name("customerStatementItemReader")
                .resource(ClassPathResource("input/records.xml")) // Adjust the path to your XML file
                .addFragmentRootElements("record")
                .unmarshaller(marshaller)
                .build()
    }
}