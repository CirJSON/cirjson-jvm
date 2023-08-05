package org.cirjson

import java.io.IOException
import java.io.StringWriter
import java.math.BigDecimal
import java.math.BigInteger

/**
 * A class representing an element of JSON. It could either be a [CirJsonComplexValue] (a [CirJsonObject] or a
 * [CirJsonArray]), a [CirJsonPrimitive], or a [CirJsonNull].
 */
abstract class CirJsonElement {

    /**
     * Returns a deep copy of this element. Immutable elements like primitives and nulls are not copied.
     */
    abstract fun deepCopy(): CirJsonElement

    /**
     * Provides a check for verifying if this element is a CirJSON array or not.
     *
     * @return true if this element is of type [CirJsonArray], false otherwise.
     */
    val isCirJsonArray: Boolean
        get() {
            return this is CirJsonArray
        }

    /**
     * Provides a check for verifying if this element is a JSON object or not.
     *
     * @return true if this element is of type [CirJsonObject], false otherwise.
     */
    val isCirJsonObject: Boolean
        get() {
            return this is CirJsonObject
        }

    /**
     * Provides a check for verifying if this element is a primitive or not.
     *
     * @return true if this element is of type [CirJsonPrimitive], false otherwise.
     */
    val isCirJsonPrimitive: Boolean
        get() {
            return this is CirJsonPrimitive<*>
        }

    /**
     * Provides a check for verifying if this element represents a null value or not.
     *
     * @return true if this element is of type [CirJsonNull], false otherwise.
     * @since 1.2
     */
    val isCirJsonNull: Boolean
        get() {
            return this is CirJsonNull
        }

    /**
     * Convenience method to get this element as a [CirJsonObject].
     *
     * If this element is of some other type, an [IllegalStateException] will result. Hence, it is best to use this
     * method after ensuring that this element is of the desired type by calling [isCirJsonObject] first.
     *
     * @return this element as a [CirJsonObject].
     * @throws IllegalStateException if this element is of another type.
     */
    fun getAsCirJsonObject(): CirJsonObject {
        if (isCirJsonObject) {
            return this as CirJsonObject
        }
        throw IllegalStateException("Not a CirJSON Object: $this")
    }

    /**
     * Convenience method to get this element as a [CirJsonArray].
     *
     * If this element is of some other type, an [IllegalStateException] will result. Hence, it is best to use this
     * method after ensuring that this element is of the desired type by calling [isCirJsonArray] first.
     *
     * @return this element as a [CirJsonArray].
     * @throws IllegalStateException if this element is of another type.
     */
    fun getAsCirJsonArray(): CirJsonArray {
        if (isCirJsonArray) {
            return this as CirJsonArray
        }
        throw IllegalStateException("Not a CirJSON Array: $this")
    }

    /**
     * Convenience method to get this element as a [CirJsonPrimitive].
     *
     * If this element is of some other type, an [IllegalStateException] will result. Hence, it is best to use this
     * method after ensuring that this element is of the desired type by calling [isCirJsonPrimitive] first.
     *
     * @return this element as a [CirJsonPrimitive].
     * @throws IllegalStateException if this element is of another type.
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getAsCirJsonPrimitive(): CirJsonPrimitive<T> {
        if (isCirJsonPrimitive) {
            return this as CirJsonPrimitive<T>
        }
        throw IllegalStateException("Not a CirJSON Primitive: $this")
    }

    /**
     * Convenience method to get this element as a [CirJsonNull].
     *
     * If this element is of some other type, an [IllegalStateException] will result. Hence, it is best to use this
     * method after ensuring that this element is of the desired type by calling [isCirJsonNull] first.
     *
     * @return this element as a [CirJsonNull].
     * @throws IllegalStateException if this element is of another type.
     */
    fun getAsCirJsonNull(): CirJsonNull {
        if (isCirJsonNull) {
            return this as CirJsonNull
        }
        throw IllegalStateException("Not a CirJSON Null: $this")
    }

    /**
     * Convenience method to get this element as a boolean value.
     *
     * @return this element as a primitive boolean value.
     * @throws UnsupportedOperationException if this element is not a [CirJsonPrimitive] or [CirJsonArray].
     * @throws IllegalStateException if this element is of the type [CirJsonArray] but contains more than a single
     * element.
     */
    open fun getAsBoolean(): Boolean {
        throw UnsupportedOperationException(javaClass.getSimpleName())
    }

    /**
     * Convenience method to get this element as a [Number].
     *
     * @return this element as a [Number].
     * @throws UnsupportedOperationException if this element is not a [CirJsonPrimitive] or [CirJsonArray],
     * or cannot be converted to a number.
     * @throws IllegalStateException if this element is of the type [CirJsonArray] but contains
     * more than a single element.
     */
    open fun getAsNumber(): Number {
        throw UnsupportedOperationException(javaClass.simpleName)
    }

    /**
     * Convenience method to get this element as a string value.
     *
     * @return this element as a string value.
     * @throws UnsupportedOperationException if this element is not a [CirJsonPrimitive] or [CirJsonArray].
     * @throws IllegalStateException if this element is of the type [CirJsonArray] but contains
     * more than a single element.
     */
    open fun getAsString(): String {
        throw UnsupportedOperationException(javaClass.getSimpleName())
    }

    /**
     * Convenience method to get this element as a primitive double value.
     *
     * @return this element as a primitive double value.
     * @throws UnsupportedOperationException if this element is not a [CirJsonPrimitive] or [CirJsonArray].
     * @throws NumberFormatException if the value contained is not a valid double.
     * @throws IllegalStateException if this element is of the type [CirJsonArray] but contains
     * more than a single element.
     */
    open fun getAsDouble(): Double {
        throw UnsupportedOperationException(javaClass.getSimpleName())
    }

    /**
     * Convenience method to get this element as a primitive float value.
     *
     * @return this element as a primitive float value.
     * @throws UnsupportedOperationException if this element is not a [CirJsonPrimitive] or [CirJsonArray].
     * @throws NumberFormatException if the value contained is not a valid float.
     * @throws IllegalStateException if this element is of the type [CirJsonArray] but contains
     * more than a single element.
     */
    open fun getAsFloat(): Float {
        throw UnsupportedOperationException(javaClass.getSimpleName())
    }

    /**
     * Convenience method to get this element as a primitive long value.
     *
     * @return this element as a primitive long value.
     * @throws UnsupportedOperationException if this element is not a [CirJsonPrimitive] or [CirJsonArray].
     * @throws NumberFormatException if the value contained is not a valid long.
     * @throws IllegalStateException if this element is of the type [CirJsonArray] but contains
     * more than a single element.
     */
    open fun getAsLong(): Long {
        throw UnsupportedOperationException(javaClass.getSimpleName())
    }

    /**
     * Convenience method to get this element as a primitive integer value.
     *
     * @return this element as a primitive integer value.
     * @throws UnsupportedOperationException if this element is not a [CirJsonPrimitive] or [CirJsonArray].
     * @throws NumberFormatException if the value contained is not a valid integer.
     * @throws IllegalStateException if this element is of the type [CirJsonArray] but contains
     * more than a single element.
     */
    open fun getAsInt(): Int {
        throw UnsupportedOperationException(javaClass.getSimpleName())
    }

    /**
     * Convenience method to get this element as a primitive short value.
     *
     * @return this element as a primitive short value.
     * @throws UnsupportedOperationException if this element is not a [CirJsonPrimitive] or [CirJsonArray].
     * @throws NumberFormatException if the value contained is not a valid short.
     * @throws IllegalStateException if this element is of the type [CirJsonArray] but contains
     * more than a single element.
     */
    open fun getAsShort(): Short {
        throw UnsupportedOperationException(javaClass.getSimpleName())
    }

    /**
     * Convenience method to get this element as a primitive byte value.
     *
     * @return this element as a primitive byte value.
     * @throws UnsupportedOperationException if this element is not a [CirJsonPrimitive] or [CirJsonArray].
     * @throws NumberFormatException if the value contained is not a valid byte.
     * @throws IllegalStateException if this element is of the type [CirJsonArray] but contains
     * more than a single element.
     */
    open fun getAsByte(): Byte {
        throw UnsupportedOperationException(javaClass.getSimpleName())
    }

    /**
     * Convenience method to get the character of the single character string value of this element.
     *
     * @return this element as a primitive char value.
     * @throws UnsupportedOperationException if this element is not a [CirJsonPrimitive] or [CirJsonArray].
     * @throws NumberFormatException if the value contained is not a valid char.
     * @throws IllegalStateException if this element is of the type [CirJsonArray] but contains
     * more than a single element or is a string with more than one character.
     */
    open fun getAsCharacter(): Char {
        throw UnsupportedOperationException(javaClass.getSimpleName())
    }

    /**
     * Convenience method to get this element as a [BigDecimal].
     *
     * @return this element as a [BigDecimal].
     * @throws UnsupportedOperationException if this element is not a [CirJsonPrimitive] or [CirJsonArray].
     * @throws NumberFormatException if this element is not a valid [BigDecimal].
     * @throws IllegalStateException if this element is of the type [CirJsonArray] but contains
     * more than a single element.
     */
    open fun getAsBigDecimal(): BigDecimal {
        throw UnsupportedOperationException(javaClass.getSimpleName())
    }

    /**
     * Convenience method to get this element as a [BigInteger].
     *
     * @return this element as a [BigInteger].
     * @throws UnsupportedOperationException if this element is not a [CirJsonPrimitive] or [CirJsonArray].
     * @throws NumberFormatException if this element is not a valid [BigInteger].
     * @throws IllegalStateException if this element is of the type [CirJsonArray] but contains
     * more than a single element.
     */
    open fun getAsBigInteger(): BigInteger {
        throw UnsupportedOperationException(javaClass.getSimpleName())
    }

    /**
     * Convenience method to get this element as a [ULong].
     *
     * @return this element as a [ULong].
     * @throws UnsupportedOperationException if this element is not a [CirJsonPrimitive] or [CirJsonArray].
     * @throws NumberFormatException if this element is not a valid [ULong].
     * @throws IllegalStateException if this element is of the type [CirJsonArray] but contains
     * more than a single element.
     */
    open fun getAsULong(): ULong {
        throw UnsupportedOperationException(javaClass.getSimpleName())
    }

    /**
     * Convenience method to get this element as a [UInt].
     *
     * @return this element as a [UInt].
     * @throws UnsupportedOperationException if this element is not a [CirJsonPrimitive] or [CirJsonArray].
     * @throws NumberFormatException if this element is not a valid [UInt].
     * @throws IllegalStateException if this element is of the type [CirJsonArray] but contains
     * more than a single element.
     */
    open fun getAsUInt(): UInt {
        throw UnsupportedOperationException(javaClass.getSimpleName())
    }

    /**
     * Convenience method to get this element as a [UShort].
     *
     * @return this element as a [UShort].
     * @throws UnsupportedOperationException if this element is not a [CirJsonPrimitive] or [CirJsonArray].
     * @throws NumberFormatException if this element is not a valid [UShort].
     * @throws IllegalStateException if this element is of the type [CirJsonArray] but contains
     * more than a single element.
     */
    open fun getAsUShort(): UShort {
        throw UnsupportedOperationException(javaClass.getSimpleName())
    }

    /**
     * Convenience method to get this element as a [UByte].
     *
     * @return this element as a [UByte].
     * @throws UnsupportedOperationException if this element is not a [CirJsonPrimitive] or [CirJsonArray].
     * @throws NumberFormatException if this element is not a valid [UByte].
     * @throws IllegalStateException if this element is of the type [CirJsonArray] but contains
     * more than a single element.
     */
    open fun getAsUByte(): UByte {
        throw UnsupportedOperationException(javaClass.getSimpleName())
    }

    /**
     * Returns a String representation of this element.
     */
    override fun toString(): String {
        return try {
            val stringWriter = StringWriter()
            val cirJsonWriter = CirJsonWriter(stringWriter)
            cirJsonWriter.setLenient(true)
            Streams.write(this, cirJsonWriter)
            stringWriter.toString()
        } catch (e: IOException) {
            throw AssertionError(e)
        }
    }

}