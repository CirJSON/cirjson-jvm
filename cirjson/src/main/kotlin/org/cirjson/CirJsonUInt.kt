package org.cirjson

class CirJsonUInt(override val value: UInt) : CirJsonPrimitive<UInt>() {

    override val isUnsigned: Boolean
        get() = true

}