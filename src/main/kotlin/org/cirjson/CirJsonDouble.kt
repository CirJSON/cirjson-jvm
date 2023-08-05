package org.cirjson

class CirJsonDouble(override val value: Double) : CirJsonPrimitive<Double>() {

    override val isNumber: Boolean
        get() = true

}