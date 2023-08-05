package org.cirjson

class CirJsonBigInteger(override val value: BigInteger) : CirJsonPrimitive<BigInteger>() {

    override val isNumber: Boolean
        get() = true

}