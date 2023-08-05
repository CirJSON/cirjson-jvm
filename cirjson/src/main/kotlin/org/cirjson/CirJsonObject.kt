package org.cirjson

import org.cirjson.internal.LinkedTreeMap
import java.math.BigDecimal
import java.math.BigInteger

/**
 * A class representing an object type in CirJson. An object consists of name-value pairs where names
 * are strings, and values are any other type of [CirJsonElement]. This allows for a creating a
 * tree of CirJsonElements. The member elements of this object are maintained in order they were added.
 * This class does not support `null` values. If `null` is provided as value argument
 * to any of the methods, it is converted to a [CirJsonNull].
 *
 * `JsonObject` does not implement the [Map] interface, but a `Map` view
 * of it can be obtained with [asMap].
 */
class CirJsonObject : CirJsonElement() {

    private val members = LinkedTreeMap<String, CirJsonElement>(false)

    /**
     * Creates a deep copy of this element and all its children.
     */
    override fun deepCopy(): CirJsonObject {
        val result = CirJsonObject()
        for (entry in members.entries) {
            result.add(entry.key, entry.value.deepCopy())
        }
        return result
    }

    /**
     * Adds a member, which is a name-value pair, to self. The name must be a String, but the value
     * can be an arbitrary [CirJsonElement], thereby allowing you to build a full tree of JsonElements
     * rooted at this node.
     *
     * @param property name of the member.
     * @param value the member object.
     */
    fun add(property: String?, value: CirJsonElement?) {
        members[property!!] = value ?: CirJsonNull
    }

    /**
     * Removes the `property` from this object.
     *
     * @param property name of the member that should be removed.
     *
     * @return the [CirJsonElement] object that is being removed, or `null` if no member with this name exists.
     */
    fun remove(property: String?): CirJsonElement? {
        return members.remove(property)
    }

    /**
     * Convenience method to add a string member. The specified value is converted to a
     * [CirJsonPrimitive] of String.
     *
     * @param property name of the member.
     * @param value the string value associated with the member.
     */
    fun addProperty(property: String?, value: String?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a number member. The specified value is converted to a
     * [CirJsonPrimitive] of Number.
     *
     * @param property name of the member.
     * @param value the number value associated with the member.
     */
    fun addProperty(property: String?, value: Number?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a boolean member. The specified value is converted to a
     * [CirJsonPrimitive] of Boolean.
     *
     * @param property name of the member.
     * @param value the boolean value associated with the member.
     */
    fun addProperty(property: String?, value: Boolean?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a char member. The specified value is converted to a
     * [CirJsonPrimitive] of Char.
     *
     * @param property name of the member.
     * @param value the char value associated with the member.
     */
    fun addProperty(property: String?, value: Char?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a double member. The specified value is converted to a
     * [CirJsonPrimitive] of Double.
     *
     * @param property name of the member.
     * @param value the double value associated with the member.
     */
    fun addProperty(property: String?, value: Double?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a float member. The specified value is converted to a
     * [CirJsonPrimitive] of Float.
     *
     * @param property name of the member.
     * @param value the float value associated with the member.
     */
    fun addProperty(property: String?, value: Float?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a long member. The specified value is converted to a
     * [CirJsonPrimitive] of Long.
     *
     * @param property name of the member.
     * @param value the long value associated with the member.
     */
    fun addProperty(property: String?, value: Long?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a int member. The specified value is converted to a
     * [CirJsonPrimitive] of Int.
     *
     * @param property name of the member.
     * @param value the int value associated with the member.
     */
    fun addProperty(property: String?, value: Int?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a short member. The specified value is converted to a
     * [CirJsonPrimitive] of Short.
     *
     * @param property name of the member.
     * @param value the short value associated with the member.
     */
    fun addProperty(property: String?, value: Short?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a byte member. The specified value is converted to a
     * [CirJsonPrimitive] of Byte.
     *
     * @param property name of the member.
     * @param value the byte value associated with the member.
     */
    fun addProperty(property: String?, value: Byte?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a bigdecimal member. The specified value is converted to a
     * [CirJsonPrimitive] of BigDecimal.
     *
     * @param property name of the member.
     * @param value the bigdecimal value associated with the member.
     */
    fun addProperty(property: String?, value: BigDecimal?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a biginteger member. The specified value is converted to a
     * [CirJsonPrimitive] of BigInteger.
     *
     * @param property name of the member.
     * @param value the biginteger value associated with the member.
     */
    fun addProperty(property: String?, value: BigInteger?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a ULong member. The specified value is converted to a
     * [CirJsonPrimitive] of ULong.
     *
     * @param property name of the member.
     * @param value the ULong value associated with the member.
     */
    fun addProperty(property: String?, value: ULong?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a UInt member. The specified value is converted to a
     * [CirJsonPrimitive] of UInt.
     *
     * @param property name of the member.
     * @param value the UInt value associated with the member.
     */
    fun addProperty(property: String?, value: UInt?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a UShort member. The specified value is converted to a
     * [CirJsonPrimitive] of UShort.
     *
     * @param property name of the member.
     * @param value the UShort value associated with the member.
     */
    fun addProperty(property: String?, value: UShort?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a UByte member. The specified value is converted to a
     * [CirJsonPrimitive] of UByte.
     *
     * @param property name of the member.
     * @param value the UByte value associated with the member.
     */
    fun addProperty(property: String?, value: UByte?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Returns a set of members of this object. The set is ordered, and the order is in which the
     * elements were added.
     *
     * @return a set of members of this object.
     */
    val entries: Set<Map.Entry<String, CirJsonElement>>
        get() = members.entries

    /**
     * Returns a set of members key values.
     *
     * @return a set of member keys as Strings
     */
    val keys: Set<String>
        get() = members.keys

    /**
     * Returns the number of key/value pairs in the object.
     *
     * @return the number of key/value pairs in the object.
     */
    val size: Int
        get() = members.size

    /**
     * Returns true if the number of key/value pairs in the object is zero.
     *
     * @return true if the number of key/value pairs in the object is zero.
     */
    fun isEmpty(): Boolean {
        return members.isEmpty()
    }

    /**
     * Returns true if the number of key/value pairs in the object is not zero.
     *
     * @return true if the number of key/value pairs in the object is not zero.
     */
    fun isNotEmpty(): Boolean {
        return members.isNotEmpty()
    }

    /**
     * Convenience method to check if a member with the specified name is present in this object.
     *
     * @param memberName name of the member that is being checked for presence.
     *
     * @return true if there is a member with the specified name, false otherwise.
     */
    fun containsKey(memberName: String): Boolean {
        return members.containsKey(memberName)
    }

    /**
     * Returns the member with the specified name.
     *
     * @param memberName name of the member that is being requested.
     *
     * @return the member matching the name, or `null` if no such member exists.
     */
    operator fun get(memberName: String): CirJsonElement? {
        return members[memberName]
    }

    /**
     * Convenience method to get the specified member as a [CirJsonPrimitive].
     *
     * @param memberName name of the member being requested.
     *
     * @return the `CirJsonPrimitive` corresponding to the specified member, or `null` if no
     *   member with this name exists.
     *
     * @throws ClassCastException if the member is not of type `CirJsonPrimitive`.
     */
    fun getAsCirJsonPrimitive(memberName: String): CirJsonPrimitive<*>? {
        return members[memberName] as CirJsonPrimitive<*>?
    }

    /**
     * Convenience method to get the specified member as a [CirJsonArray].
     *
     * @param memberName name of the member being requested.
     *
     * @return the `CirJsonArray` corresponding to the specified member, or `null` if no
     *   member with this name exists.
     *
     * @throws ClassCastException if the member is not of type `CirJsonArray`.
     */
    fun getAsCirJsonArray(memberName: String): CirJsonArray? {
        return members[memberName] as CirJsonArray?
    }

    /**
     * Returns a mutable [MutableMap] view of this `CirJsonObject`. Changes to the `MutableMap`
     * are visible in this `CirJsonObject` and the other way around.
     *
     * The `MutableMap` does not permit `null` keys or values. Unlike `CirJsonObject`'s
     * `null` handling, a [NullPointerException] is thrown when trying to add `null`.
     * Use [CirJsonNull] for CirJSON null values.
     *
     * @return `MutableMap` view
     */
    fun asMap(): MutableMap<String, CirJsonElement> {
        // It is safe to expose the underlying map because it disallows null keys and values
        return members
    }

    /**
     * Returns whether the other object is equal to this. This method only considers
     * the other object to be equal if it is an instance of `CirJsonObject` and has
     * equal members, ignoring order.
     */
    override fun equals(other: Any?): Boolean {
        return other === this || other is CirJsonObject && other.members == members
    }

    /**
     * Returns the hash code of this object. This method calculates the hash code based
     * on the members of this object, ignoring order.
     */
    override fun hashCode(): Int {
        return members.hashCode()
    }

}