package org.cirjson

class CirJsonULong(override val value: ULong) : CirJsonPrimitive<ULong>() {

    override val isUnsigned: Boolean
        get() = true

}