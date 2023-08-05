package org.cirjson

class CirJsonShort(override val value: Short) : CirJsonPrimitive<Short>() {

    override val isNumber: Boolean
        get() = true

}