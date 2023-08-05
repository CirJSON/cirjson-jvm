package org.cirjson

class CirJsonBoolean(override val value: Boolean) : CirJsonPrimitive<Boolean>() {

    override val isBoolean: Boolean
        get() = true

}