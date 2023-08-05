package org.cirjson

class CirJsonByte(override val value: Byte) : CirJsonPrimitive<Byte>() {

    override val isNumber: Boolean
        get() = true

}