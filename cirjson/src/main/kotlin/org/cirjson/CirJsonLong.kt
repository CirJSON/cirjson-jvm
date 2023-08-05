package org.cirjson

class CirJsonLong(override val value: Long) : CirJsonPrimitive<Long>() {

    override val isNumber: Boolean
        get() = true

}