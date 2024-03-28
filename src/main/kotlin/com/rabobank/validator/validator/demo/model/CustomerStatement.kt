package com.rabobank.validator.validator.demo.model


import jakarta.xml.bind.annotation.*
import java.math.BigDecimal

/*
* To defend myself, i'm not typically a fan of using a model that is clearly built as a DTO like it is at this point as
* the data type that i am also using for processing.
* Yet i was looking at this demo and seeing that if i add the extra steps to mapping over what is essentially
* a data type i was to name CustomerStatementRecord to CustomerStatement
* i would have been adding a lot of code, in this case, for the sake of adding code.
* We ultimately trust the source of data and when we are processing the csv file
* we are processing an object of the exact same type, yet ignoring all of the XML related annotations.
* This is why i did not create a separate CustomerStatement and CustomerStatementRecord.
* In a real world use case i would map them over.
*
* */
@XmlRootElement(name = "record")
@XmlAccessorType(XmlAccessType.FIELD)
class CustomerStatement {
    @XmlAttribute
    var reference: Long? = null
    @XmlElement(name="accountNumber")
    var accountNumber: String? = null
    @XmlElement(name="description")
    var description: String? = null
    @XmlElement(name="startBalance")
    var startBalance: BigDecimal? = null
    @XmlElement(name="mutation")
    var mutation: BigDecimal? = null
    @XmlElement(name="endBalance")
    var endBalance: BigDecimal? = null

    override fun toString(): String {
        return "CustomerStatement(reference=$reference, accountNumber='$accountNumber', description='$description', startBalance=$startBalance, mutation=$mutation, endBalance=$endBalance)"
    }
}