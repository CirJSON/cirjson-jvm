package org.cirjson

import org.cirjson.internal.LinkedTreeMap
import java.math.BigDecimal
import java.math.BigInteger

/**
 * A class representing an object type in CirJson. An object consists of name-value pairs where names
 * are strings, and values are any other type of {@link CirJsonElement}. This allows for a creating a
 * tree of CirJsonElements. The member elements of this object are maintained in order they were added.
 * This class does not support {@code null} values. If {@code null} is provided as value argument
 * to any of the methods, it is converted to a {@link CirJsonNull}.
 *
 * {@code JsonObject} does not implement the {@link Map} interface, but a {@code Map} view
 * of it can be obtained with {@link #asMap()}.
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
     * can be an arbitrary {@link CirJsonElement}, thereby allowing you to build a full tree of JsonElements
     * rooted at this node.
     *
     * @param property name of the member.
     * @param value the member object.
     */
    fun add(property: String?, value: CirJsonElement?) {
        members[property!!] = value ?: CirJsonNull
    }

    /**
     * Removes the {@code property} from this object.
     *
     * @param property name of the member that should be removed.
     *
     * @return the {@link JsonElement} object that is being removed, or {@code null} if no member with this name exists.
     */
    fun remove(property: String?): CirJsonElement? {
        return members.remove(property)
    }

    /**
     * Convenience method to add a string member. The specified value is converted to a
     * {@link JsonPrimitive} of String.
     *
     * @param property name of the member.
     * @param value the string value associated with the member.
     */
    fun addProperty(property: String?, value: String?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a number member. The specified value is converted to a
     * {@link JsonPrimitive} of Number.
     *
     * @param property name of the member.
     * @param value the number value associated with the member.
     */
    fun addProperty(property: String?, value: Number?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a boolean member. The specified value is converted to a
     * {@link JsonPrimitive} of Boolean.
     *
     * @param property name of the member.
     * @param value the number value associated with the member.
     */
    fun addProperty(property: String?, value: Boolean?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a char member. The specified value is converted to a
     * {@link JsonPrimitive} of Char.
     *
     * @param property name of the member.
     * @param value the number value associated with the member.
     */
    fun addProperty(property: String?, value: Char?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a double member. The specified value is converted to a
     * {@link CirJsonPrimitive} of Double.
     *
     * @param property name of the member.
     * @param value the double value associated with the member.
     */
    fun addProperty(property: String?, value: Double?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a float member. The specified value is converted to a
     * {@link CirJsonPrimitive} of Float.
     *
     * @param property name of the member.
     * @param value the float value associated with the member.
     */
    fun addProperty(property: String?, value: Float?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a long member. The specified value is converted to a
     * {@link CirJsonPrimitive} of Long.
     *
     * @param property name of the member.
     * @param value the long value associated with the member.
     */
    fun addProperty(property: String?, value: Long?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a int member. The specified value is converted to a
     * {@link CirJsonPrimitive} of Int.
     *
     * @param property name of the member.
     * @param value the int value associated with the member.
     */
    fun addProperty(property: String?, value: Int?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a short member. The specified value is converted to a
     * {@link CirJsonPrimitive} of Short.
     *
     * @param property name of the member.
     * @param value the short value associated with the member.
     */
    fun addProperty(property: String?, value: Short?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a byte member. The specified value is converted to a
     * {@link CirJsonPrimitive} of Byte.
     *
     * @param property name of the member.
     * @param value the byte value associated with the member.
     */
    fun addProperty(property: String?, value: Byte?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a bigdecimal member. The specified value is converted to a
     * {@link CirJsonPrimitive} of BigDecimal.
     *
     * @param property name of the member.
     * @param value the bigdecimal value associated with the member.
     */
    fun addProperty(property: String?, value: BigDecimal?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a biginteger member. The specified value is converted to a
     * {@link CirJsonPrimitive} of BigInteger.
     *
     * @param property name of the member.
     * @param value the biginteger value associated with the member.
     */
    fun addProperty(property: String?, value: BigInteger?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a ULong member. The specified value is converted to a
     * {@link CirJsonPrimitive} of ULong.
     *
     * @param property name of the member.
     * @param value the ULong value associated with the member.
     */
    fun addProperty(property: String?, value: ULong?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a UInt member. The specified value is converted to a
     * {@link CirJsonPrimitive} of UInt.
     *
     * @param property name of the member.
     * @param value the UInt value associated with the member.
     */
    fun addProperty(property: String?, value: UInt?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a UShort member. The specified value is converted to a
     * {@link CirJsonPrimitive} of UShort.
     *
     * @param property name of the member.
     * @param value the UShort value associated with the member.
     */
    fun addProperty(property: String?, value: UShort?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

    /**
     * Convenience method to add a UByte member. The specified value is converted to a
     * {@link CirJsonPrimitive} of UByte.
     *
     * @param property name of the member.
     * @param value the UByte value associated with the member.
     */
    fun addProperty(property: String?, value: UByte?) {
        add(property, if (value == null) CirJsonNull else CirJsonPrimitive(value))
    }

}