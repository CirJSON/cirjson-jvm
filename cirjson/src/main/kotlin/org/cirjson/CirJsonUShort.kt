package org.cirjson

class CirJsonUShort(override val value: UShort) : CirJsonPrimitive<UShort>() {

    override val isUnsigned: Boolean
        get() = true

}