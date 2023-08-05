package org.cirjson

import java.lang.reflect.Field
import java.lang.reflect.Type
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.javaField

/**
 * A data class that stores attributes of a property.
 *
 * This class is immutable; therefore, it can be safely shared across threads.
 */
data class FieldAttributes(private val property: KProperty<*>) {

    private val field: Field

    init {
        if (this.property.javaField == null) {
            throw IllegalStateException("the property must have a field")
        }

        this.field = this.property.javaField!!
    }

    /**
     * Gets the declaring Class that contains this property
     *
     * @return the declaring class that contains this property
     */
    val declaringClass: Class<*>
        get() {
            return this.field.declaringClass
        }

    /**
     * Gets the name of the field
     *
     * @return the name of the field
     */
    val name: String
        get() {
            return this.field.name
        }

    /**
     * For example, assume the following class definition:
     *
     * ```
     * class Foo {
     *   val bar: String
     *   private val red: List<String>
     * }
     *
     * val listParameterizedType = object : TypeToken<List<String>>() {}.getType()
     * ```
     *
     * This method would return `String.class` for the `bar` field and `listParameterizedType` for the `red` field.
     *
     * @return the specific type declared for this field
     */
    val declaredType: Type
        get() {
            return this.field.genericType
        }

    /**
     * Returns the `Class` object that was declared for this field.
     *
     * For example, assume the following class definition:
     *
     * ```
     * class Foo {
     *   val bar: String
     *   private val red: List<String>
     * }
     * ```
     *
     * This method would return `String::class.java` for the `bar` field and `List::class.java` for the `red` field.
     *
     * @return the specific class object that was declared for the field
     */
    val declaredClass: Class<*>
        get() {
            return this.field.type
        }

    /**
     * Return the `T` annotation object from this field if it exists; otherwise returns `null`.
     *
     * @param annotation the class of the annotation that will be retrieved
     *
     * @return the annotation instance if it is bound to the field; otherwise `null`
     */
    fun <T : Annotation> getAnnotation(annotation: Class<T>): T {
        return this.field.getAnnotation(annotation)
    }

    /**
     * Return the annotations that are present on this field.
     *
     * @return an array of all the annotations set on the field
     */
    val annotations: Collection<Annotation>
        get() {
            return this.field.annotations.asList()
        }

    /**
     * Returns `true` if the field is defined with the `modifier`.
     *
     * This method is meant to be called as:
     *
     * ```
     * val hasPublicModifier = fieldAttribute.hasModifier(java.lang.reflect.Modifier.PUBLIC)
     * ```
     *
     * @see java.lang.reflect.Modifier
     */
    fun hasModifier(modifier: Int): Boolean {
        return this.field.modifiers and modifier != 0
    }

    override fun toString(): String {
        return this.field.toString()
    }

}
