package com.rabobank.validator.validator.demo.model


import jakarta.xml.bind.annotation.*
import java.math.BigDecimal

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