package org.cirjson

class CirJsonBigDecimal(override val value: BigDecimal) : CirJsonPrimitive<BigDecimal>() {

    override val isNumber: Boolean
        get() = true

}