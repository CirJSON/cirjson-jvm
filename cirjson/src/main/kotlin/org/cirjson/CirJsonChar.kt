package org.cirjson

class CirJsonChar(override val value: Char) : CirJsonPrimitive<Char>() {

    override val isString: Boolean
        get() = true

    override val isChar: Boolean
        get() = true

}