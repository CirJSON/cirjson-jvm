package org.cirjson

import java.math.BigDecimal
import java.math.BigInteger

fun Any?.identityHashCode(): Int {
    return System.identityHashCode(this)
}

@Suppress("UNCHECKED_CAST")
fun <T : Any> CirJsonPrimitive(value: T): CirJsonPrimitive<T> {
    if (value is Boolean) {
        return CirJsonBoolean(value) as CirJsonPrimitive<T>
    }

    if (value is Char) {
        return CirJsonChar(value) as CirJsonPrimitive<T>
    }

    if (value is String) {
        return CirJsonString(value) as CirJsonPrimitive<T>
    }

    if (value is Double) {
        return CirJsonDouble(value) as CirJsonPrimitive<T>
    }

    if (value is Float) {
        return CirJsonFloat(value) as CirJsonPrimitive<T>
    }

    if (value is Long) {
        return CirJsonLong(value) as CirJsonPrimitive<T>
    }

    if (value is Int) {
        return CirJsonInt(value) as CirJsonPrimitive<T>
    }

    if (value is Short) {
        return CirJsonShort(value) as CirJsonPrimitive<T>
    }

    if (value is Byte) {
        return CirJsonByte(value) as CirJsonPrimitive<T>
    }

    if (value is BigDecimal) {
        return CirJsonBigDecimal(value) as CirJsonPrimitive<T>
    }

    if (value is BigInteger) {
        return CirJsonBigInteger(value) as CirJsonPrimitive<T>
    }

    if (value is ULong) {
        return CirJsonULong(value) as CirJsonPrimitive<T>
    }

    if (value is UInt) {
        return CirJsonUInt(value) as CirJsonPrimitive<T>
    }

    if (value is UShort) {
        return CirJsonUShort(value) as CirJsonPrimitive<T>
    }

    if (value is UByte) {
        return CirJsonUByte(value) as CirJsonPrimitive<T>
    }

    throw IllegalStateException()
}