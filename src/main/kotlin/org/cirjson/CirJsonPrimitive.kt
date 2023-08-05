package org.cirjson

import java.math.BigDecimal
import java.math.BigInteger

/**
 * A class representing a CirJSON primitive value. A primitive value is either a String, a Java primitive, a Java
 * primitive wrapper type, or a kotlin unsigned number.
 */
sealed class CirJsonPrimitive<T : Any> : CirJsonElement() {

    protected abstract val value: T

    override fun deepCopy(): CirJsonElement {
        return this
    }

    /**
     * Whether this primitive contains a boolean value.
     */
    open val isBoolean: Boolean
        get() = false

    /**
     * Convenience method to get this element as a boolean value.
     *
     * If this primitive [is not a boolean][isBoolean], the string value
     * is parsed using [toBoolean]. This means `"true"` (ignoring
     * case) is considered `true` and any other value is considered `false`.
     */
    override fun getAsBoolean(): Boolean {
        if (this is CirJsonBoolean) {
            return this.value as Boolean
        }

        if (isString) {
            return (value as String).toBoolean()
        }

        return super.getAsBoolean()
    }

    /**
     * Whether this primitive contains a [Number] or an unsigned integer.
     */
    open val isNumber: Boolean
        get() = false

    /**
     * Convenience method to get this element as a [Number].
     *
     * If this primitive [is a string][isString] or [is an unsigned number][isUnsigned], a lazily parsed `Number`
     * is constructed which parses the string when any of its methods are called (which can
     * lead to a [NumberFormatException]).
     *
     * @throws UnsupportedOperationException if this primitive is neither a number nor a string.
     */
    override fun getAsNumber(): Number {
        if (value is Number) {
            return value as Number
        }

        if (value is String) {
            return LazilyParsedNumber(value as String)
        }

        if (isUnsigned) {
            return LazilyParsedNumber(value.toString())
        }

        return super.getAsNumber()
    }

    /**
     * Whether this primitive contains a [String].
     */
    open val isString: Boolean
        get() = false

    override fun getAsString(): String {
        if (value is String) {
            return value as String
        }

        if (isChar || isBoolean || isUnsigned) {
            return value.toString()
        }

        throw AssertionError("Unexpected value type: " + value.javaClass)
    }

    override fun getAsDouble(): Double {
        return if (isNumber) getAsNumber().toDouble() else getAsString().toDouble()
    }

    override fun getAsFloat(): Float {
        return if (isNumber) getAsNumber().toFloat() else getAsString().toFloat()
    }

    override fun getAsLong(): Long {
        val v = value
        if (v is String) {
            return v.toLong()
        }

        if (isUnsigned) {
            if (v is ULong) {
                return v.toLong()
            }

            if (v is UInt) {
                return v.toLong()
            }

            if (v is UShort) {
                return v.toLong()
            }

            if (v is UByte) {
                return v.toLong()
            }
        }

        return getAsString().toLong()
    }

    override fun getAsInt(): Int {
        val v = value
        if (v is String) {
            return v.toInt()
        }

        if (isUnsigned) {
            if (v is ULong) {
                return v.toInt()
            }

            if (v is UInt) {
                return v.toInt()
            }

            if (v is UShort) {
                return v.toInt()
            }

            if (v is UByte) {
                return v.toInt()
            }
        }

        return getAsString().toInt()
    }

    override fun getAsShort(): Short {
        val v = value
        if (v is String) {
            return v.toShort()
        }

        if (isUnsigned) {
            if (v is ULong) {
                return v.toShort()
            }

            if (v is UInt) {
                return v.toShort()
            }

            if (v is UShort) {
                return v.toShort()
            }

            if (v is UByte) {
                return v.toShort()
            }
        }

        return getAsString().toShort()
    }

    override fun getAsByte(): Byte {
        val v = value
        if (v is String) {
            return v.toByte()
        }

        if (isUnsigned) {
            if (v is ULong) {
                return v.toByte()
            }

            if (v is UInt) {
                return v.toByte()
            }

            if (v is UShort) {
                return v.toByte()
            }

            if (v is UByte) {
                return v.toByte()
            }
        }

        return getAsString().toByte()
    }

    override fun getAsBigDecimal(): BigDecimal {
        val v = value
        if (v is BigDecimal) {
            return v
        }

        return BigDecimal(v.toString())
    }

    override fun getAsBigInteger(): BigInteger {
        val v = value

        if (v is BigInteger) {
            return v
        }

        if (v is Number) {
            return BigInteger(v.toLong().toString())
        }

        if (v is ULong) {
            return BigInteger(v.toLong().toString())
        }

        if (v is UInt) {
            return BigInteger(v.toLong().toString())
        }

        if (v is UShort) {
            return BigInteger(v.toLong().toString())
        }

        if (v is UByte) {
            return BigInteger(v.toLong().toString())
        }

        return BigInteger(getAsString())
    }

    override fun getAsULong(): ULong {
        val v = value

        if (v is ULong) {
            return v
        }

        if (v is Number) {
            return v.toLong().toULong()
        }

        if (v is UInt) {
            return v.toULong()
        }

        if (v is UShort) {
            return v.toULong()
        }

        if (v is UByte) {
            return v.toULong()
        }

        return getAsString().toULong()
    }

    override fun getAsUInt(): UInt {
        val v = value

        if (v is UInt) {
            return v
        }

        if (v is Number) {
            return v.toLong().toUInt()
        }

        if (v is ULong) {
            return v.toUInt()
        }

        if (v is UShort) {
            return v.toUInt()
        }

        if (v is UByte) {
            return v.toUInt()
        }

        return getAsString().toUInt()
    }

    override fun getAsUShort(): UShort {
        val v = value

        if (v is UShort) {
            return v
        }

        if (v is Number) {
            return v.toLong().toUShort()
        }

        if (v is ULong) {
            return v.toUShort()
        }

        if (v is UInt) {
            return v.toUShort()
        }

        if (v is UByte) {
            return v.toUShort()
        }

        return getAsString().toUShort()
    }

    override fun getAsUByte(): UByte {
        val v = value

        if (v is UByte) {
            return v
        }

        if (v is Number) {
            return v.toLong().toUByte()
        }

        if (v is ULong) {
            return v.toUByte()
        }

        if (v is UInt) {
            return v.toUByte()
        }

        if (v is UShort) {
            return v.toUByte()
        }

        return getAsString().toUByte()
    }

    /**
     * Whether this primitive contains a [Char].
     */
    open val isChar: Boolean
        get() = false

    override fun getAsCharacter(): Char {
        val v = value

        if (v is Char) {
            return v
        }

        val s = v.toString()

        if (s.length != 1) {
            throw UnsupportedOperationException("String value is not 1")
        }

        return s[0]
    }

    /**
     * Whether this primitive contains an unsigned number.
     */
    open val isUnsigned: Boolean
        get() = false

    override fun hashCode(): Int {
        return this.value.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }

        if (other !is CirJsonPrimitive<*>) {
            return false
        }

        return this.value == other.value
    }

}