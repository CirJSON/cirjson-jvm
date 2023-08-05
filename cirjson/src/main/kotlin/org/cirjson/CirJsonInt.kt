package org.cirjson

class CirJsonInt(override val value: Int) : CirJsonPrimitive<Int>() {

    override val isNumber: Boolean
        get() = true

}