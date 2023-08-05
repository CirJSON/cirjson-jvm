package org.cirjson

class CirJsonString(override val value: String) : CirJsonPrimitive<String>() {

    override val isString: Boolean
        get() = true

}