package org.cirjson

import java.math.BigDecimal
import java.math.BigInteger

/**
 * A class representing an array type in CirJSON. An array is a list of [CirJsonElement]s each of which can be of a
 * different type. This is an ordered list, meaning that the order in which elements are added is preserved. This class
 * does not support `null` values. If `null` is provided as element argument to any of the methods, it is converted to a
 * [CirJsonNull].
 *
 * `CirJsonArray` only implements the [Iterable] interface but not the [List] interface. A `List` view of it can be
 * obtained with [asList].
 */
class CirJsonArray : CirJsonComplexValue, Iterable<CirJsonElement> {

    private val elements: ArrayList<CirJsonElement>

    constructor() : super() {
        this.elements = ArrayList()
    }

    constructor(initialCapacity: Int) : super() {
        this.elements = ArrayList(initialCapacity)
    }

    override fun deepCopy(): CirJsonElement {
        if (elements.isNotEmpty()) {
            val result: CirJsonArray = CirJsonArray(elements.size)
            for (element in elements) {
                result.add(element.deepCopy())
            }
            return result
        }
        return CirJsonArray()
    }

    /**
     * Adds the specified boolean to self.
     *
     * @param bool the boolean that needs to be added to the array.
     */
    fun add(bool: Boolean?) {
        elements.add(if (bool == null) CirJsonNull else CirJsonBoolean(bool))
    }

    /**
     * Adds the specified character to self.
     *
     * @param character the character that needs to be added to the array.
     */
    fun add(character: Char?) {
        elements.add(if (character == null) CirJsonNull else CirJsonChar(character))
    }

    /**
     * Adds the specified number to self.
     *
     * @param number the number that needs to be added to the array.
     */
    fun add(number: Number?) {
        if (number == null) {
            elements.add(CirJsonNull)
            return
        }

        val primitive: CirJsonPrimitive<*> = when (number) {
            is Double -> CirJsonDouble(number)
            is Float -> CirJsonFloat(number)
            is Long -> CirJsonLong(number)
            is Int -> CirJsonInt(number)
            is Short -> CirJsonShort(number)
            is Byte -> CirJsonByte(number)
            is BigDecimal -> CirJsonBigDecimal(number)
            is BigInteger -> CirJsonBigInteger(number)
            else -> throw IllegalStateException()
        }

        elements.add(primitive)
    }

    /**
     * Adds the specified string to self.
     *
     * @param string the string that needs to be added to the array.
     */
    fun add(string: String?) {
        elements.add(if (string == null) CirJsonNull else CirJsonString(string))
    }

    /**
     * Adds the specified unsigned long to self.
     *
     * @param uLong the ULong that needs to be added to the array.
     */
    fun add(uLong: ULong?) {
        elements.add(if (uLong == null) CirJsonNull else CirJsonULong(uLong))
    }

    /**
     * Adds the specified unsigned int to self.
     *
     * @param uInt the UInt that needs to be added to the array.
     */
    fun add(uInt: UInt?) {
        elements.add(if (uInt == null) CirJsonNull else CirJsonUInt(uInt))
    }

    /**
     * Adds the specified unsigned short to self.
     *
     * @param uShort the UShort that needs to be added to the array.
     */
    fun add(uShort: UShort?) {
        elements.add(if (uShort == null) CirJsonNull else CirJsonUShort(uShort))
    }

    /**
     * Adds the specified unsigned byte to self.
     *
     * @param uByte the UByte that needs to be added to the array.
     */
    fun add(uByte: UByte?) {
        elements.add(if (uByte == null) CirJsonNull else CirJsonUByte(uByte))
    }

    /**
     * Adds the specified element to self.
     *
     * @param element the element that needs to be added to the array.
     */
    fun add(element: CirJsonElement?) {
        elements.add(element ?: CirJsonNull)
    }

    /**
     * Adds all the elements of the specified array to self.
     *
     * @param array the array whose elements need to be added to the array.
     */
    fun addAll(array: CirJsonArray) {
        elements.addAll(array.elements)
    }

    /**
     * Replaces the element at the specified position in this array with the specified element.
     *
     * @param index index of the element to replace
     * @param element element to be stored at the specified position
     *
     * @return the element previously at the specified position
     *
     * @throws IndexOutOfBoundsException if the specified index is outside the array bounds
     */
    operator fun set(index: Int, element: CirJsonElement?): CirJsonElement {
        return this.elements.set(index, element ?: CirJsonNull)
    }

    /**
     * Removes the first occurrence of the specified element from this array, if it is present.
     * If the array does not contain the element, it is unchanged.
     *
     * @param element element to be removed from this array, if present
     * @return true if this array contained the specified element, false otherwise
     */
    fun remove(element: CirJsonElement?): Boolean {
        return elements.remove(element)
    }

    /**
     * Removes the element at the specified position in this array. Shifts any subsequent elements
     * to the left (subtracts one from their indices). Returns the element that was removed from
     * the array.
     *
     * @param index index the index of the element to be removed
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if the specified index is outside the array bounds
     */
    fun remove(index: Int): CirJsonElement {
        return elements.removeAt(index)
    }

    /**
     * Returns true if this array contains the specified element.
     *
     * @return true if this array contains the specified element.
     * @param element whose presence in this array is to be tested
     */
    operator fun contains(element: CirJsonElement?): Boolean {
        return element in elements
    }

    /**
     * Returns the number of elements in the array.
     *
     * @return the number of elements in the array.
     */
    val size: Int
        get() {
            return elements.size
        }

    /**
     * Returns true if the array is empty.
     *
     * @return true if the array is empty.
     */
    fun isEmpty(): Boolean {
        return elements.isEmpty()
    }

    /**
     * Returns true if the array is not empty.
     *
     * @return true if the array is not empty.
     */
    fun isNotEmpty(): Boolean {
        return elements.isNotEmpty()
    }

    /**
     * Returns an iterator to navigate the elements of the array. Since the array is an ordered list,
     * the iterator navigates the elements in the order they were inserted.
     *
     * @return an iterator to navigate the elements of the array.
     */
    override fun iterator(): Iterator<CirJsonElement> {
        return elements.iterator()
    }

    /**
     * Returns the i-th element of the array.
     *
     * @param i the index of the element that is being sought.
     *
     * @return the element present at the i-th index.
     *
     * @throws IndexOutOfBoundsException if [i] is negative or greater than or equal to the [size] of the array.
     */
    operator fun get(i: Int): CirJsonElement {
        return elements[i]
    }

    private fun getAsSingleElement(): CirJsonElement {
        val size = elements.size
        if (size == 1) {
            return elements[0]
        }
        throw IllegalStateException("Array must have size 1, but has size $size")
    }

    /**
     * Convenience method to get this array as a [Boolean] if it contains a single element.
     * This method calls [CirJsonElement.getAsBoolean] on the element, therefore any
     * of the exceptions declared by that method can occur.
     *
     * @return this element as a boolean if it is single element array.
     *
     * @throws IllegalStateException if the array is empty or has more than one element.
     */
    override fun getAsBoolean(): Boolean {
        return getAsSingleElement().getAsBoolean()
    }

    /**
     * Convenience method to get this array as a [Number] if it contains a single element.
     * This method calls [CirJsonElement.getAsNumber] on the element, therefore any
     * of the exceptions declared by that method can occur.
     *
     * @return this element as a number if it is single element array.
     *
     * @throws IllegalStateException if the array is empty or has more than one element.
     */
    override fun getAsNumber(): Number {
        return getAsSingleElement().getAsNumber()
    }

    /**
     * Convenience method to get this array as a [String] if it contains a single element.
     * This method calls [CirJsonElement.getAsString] on the element, therefore any
     * of the exceptions declared by that method can occur.
     *
     * @return this element as a string if it is single element array.
     *
     * @throws IllegalStateException if the array is empty or has more than one element.
     */
    override fun getAsString(): String {
        return getAsSingleElement().getAsString()
    }

    /**
     * Convenience method to get this array as a [Char] if it contains a single element.
     * This method calls [CirJsonElement.getAsCharacter] on the element, therefore any
     * of the exceptions declared by that method can occur.
     *
     * @return this element as a char if it is single element array.
     *
     * @throws IllegalStateException if the array is empty or has more than one element.
     */
    override fun getAsCharacter(): Char {
        return getAsSingleElement().getAsCharacter()
    }

    /**
     * Convenience method to get this array as a [Double] if it contains a single element.
     * This method calls [CirJsonElement.getAsDouble] on the element, therefore any
     * of the exceptions declared by that method can occur.
     *
     * @return this element as a double if it is single element array.
     *
     * @throws IllegalStateException if the array is empty or has more than one element.
     */
    override fun getAsDouble(): Double {
        return getAsSingleElement().getAsDouble()
    }

    /**
     * Convenience method to get this array as a [Float] if it contains a single element.
     * This method calls [CirJsonElement.getAsFloat] on the element, therefore any
     * of the exceptions declared by that method can occur.
     *
     * @return this element as a float if it is single element array.
     *
     * @throws IllegalStateException if the array is empty or has more than one element.
     */
    override fun getAsFloat(): Float {
        return getAsSingleElement().getAsFloat()
    }

    /**
     * Convenience method to get this array as a [Long] if it contains a single element.
     * This method calls [CirJsonElement.getAsLong] on the element, therefore any
     * of the exceptions declared by that method can occur.
     *
     * @return this element as a long if it is single element array.
     *
     * @throws IllegalStateException if the array is empty or has more than one element.
     */
    override fun getAsLong(): Long {
        return getAsSingleElement().getAsLong()
    }

    /**
     * Convenience method to get this array as a [Int] if it contains a single element.
     * This method calls [CirJsonElement.getAsInt] on the element, therefore any
     * of the exceptions declared by that method can occur.
     *
     * @return this element as an int if it is single element array.
     *
     * @throws IllegalStateException if the array is empty or has more than one element.
     */
    override fun getAsInt(): Int {
        return getAsSingleElement().getAsInt()
    }

    /**
     * Convenience method to get this array as a [Short] if it contains a single element.
     * This method calls [CirJsonElement.getAsShort] on the element, therefore any
     * of the exceptions declared by that method can occur.
     *
     * @return this element as a short if it is single element array.
     *
     * @throws IllegalStateException if the array is empty or has more than one element.
     */
    override fun getAsShort(): Short {
        return getAsSingleElement().getAsShort()
    }

    /**
     * Convenience method to get this array as a [Byte] if it contains a single element.
     * This method calls [CirJsonElement.getAsByte] on the element, therefore any
     * of the exceptions declared by that method can occur.
     *
     * @return this element as a byte if it is single element array.
     *
     * @throws IllegalStateException if the array is empty or has more than one element.
     */
    override fun getAsByte(): Byte {
        return getAsSingleElement().getAsByte()
    }

    /**
     * Convenience method to get this array as a [BigDecimal] if it contains a single element.
     * This method calls [CirJsonElement.getAsBigDecimal] on the element, therefore any
     * of the exceptions declared by that method can occur.
     *
     * @return this element as a BigDecimal if it is single element array.
     *
     * @throws IllegalStateException if the array is empty or has more than one element.
     */
    override fun getAsBigDecimal(): BigDecimal {
        return getAsSingleElement().getAsBigDecimal()
    }

    /**
     * Convenience method to get this array as a [BigInteger] if it contains a single element.
     * This method calls [CirJsonElement.getAsBigInteger] on the element, therefore any
     * of the exceptions declared by that method can occur.
     *
     * @return this element as a BigInteger if it is single element array.
     *
     * @throws IllegalStateException if the array is empty or has more than one element.
     */
    override fun getAsBigInteger(): BigInteger {
        return getAsSingleElement().getAsBigInteger()
    }

    /**
     * Convenience method to get this array as a [ULong] if it contains a single element.
     * This method calls [CirJsonElement.getAsULong] on the element, therefore any
     * of the exceptions declared by that method can occur.
     *
     * @return this element as a ULong if it is single element array.
     *
     * @throws IllegalStateException if the array is empty or has more than one element.
     */
    override fun getAsULong(): ULong {
        return getAsSingleElement().getAsULong()
    }

    /**
     * Convenience method to get this array as a [UInt] if it contains a single element.
     * This method calls [CirJsonElement.getAsUInt] on the element, therefore any
     * of the exceptions declared by that method can occur.
     *
     * @return this element as a UInt if it is single element array.
     *
     * @throws IllegalStateException if the array is empty or has more than one element.
     */
    override fun getAsUInt(): UInt {
        return getAsSingleElement().getAsUInt()
    }

    /**
     * Convenience method to get this array as a [UShort] if it contains a single element.
     * This method calls [CirJsonElement.getAsUShort] on the element, therefore any
     * of the exceptions declared by that method can occur.
     *
     * @return this element as a UShort if it is single element array.
     *
     * @throws IllegalStateException if the array is empty or has more than one element.
     */
    override fun getAsUShort(): UShort {
        return getAsSingleElement().getAsUShort()
    }

    /**
     * Convenience method to get this array as a [UByte] if it contains a single element.
     * This method calls [CirJsonElement.getAsUByte] on the element, therefore any
     * of the exceptions declared by that method can occur.
     *
     * @return this element as a UByte if it is single element array.
     *
     * @throws IllegalStateException if the array is empty or has more than one element.
     */
    override fun getAsUByte(): UByte {
        return getAsSingleElement().getAsUByte()
    }

    /**
     * Returns a [MutableList] view of this `CirJsonArray`. Changes to the `List`
     * are visible in this `CirJsonArray` and the other way around.
     *
     * The `List` does not permit `null` elements. Unlike `CirJsonArray`'s
     * `null` handling, a [NullPointerException] is thrown when trying to add `null`.
     * Use [CirJsonNull] for CirJSON null values.
     *
     * @return `MutableList` view
     */
    fun asList(): MutableList<CirJsonElement> {
        return NonNullElementWrapperList(elements)
    }

    /**
     * Returns whether the other object is equal to this. This method only considers
     * the other object to be equal if it is an instance of `CirJsonArray` and has
     * equal elements in the same order.
     */
    override fun equals(other: Any?): Boolean {
        return other === this || other is CirJsonArray && other.elements == elements
    }

    /**
     * Returns the hash code of this array. This method calculates the hash code based
     * on the elements of this array.
     */
    override fun hashCode(): Int {
        return elements.hashCode()
    }

}