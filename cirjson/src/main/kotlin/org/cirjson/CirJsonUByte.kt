package org.cirjson

class CirJsonUByte(override val value: UByte) : CirJsonPrimitive<UByte>() {

    override val isUnsigned: Boolean
        get() = true

}