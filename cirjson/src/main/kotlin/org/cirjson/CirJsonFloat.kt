package org.cirjson

class CirJsonFloat(override val value: Float) : CirJsonPrimitive<Float>() {

    override val isNumber: Boolean
        get() = true

}